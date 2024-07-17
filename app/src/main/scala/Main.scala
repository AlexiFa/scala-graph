import zio.*
import zio.json.*
import graphs.*
import scala.io.Source
//import graphs.GraphVizExtensions._

object Main extends ZIOAppDefault {
  def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] = {
    val diGraphPath = "app/src/test/resources/directed_graph.json"
    val undirectedGraphPath = "app/src/test/resources/undirected_graph.json"
    val weightedGraphPath = "app/src/test/resources/weighted_graph.json"

    // Read the JSON content from the file
    val diGraphJson = Source.fromFile(diGraphPath).mkString
    val undirectedGraphJson = Source.fromFile(undirectedGraphPath).mkString
    val weightedGraphJson = Source.fromFile(weightedGraphPath).mkString

    // Parse the JSON content to create a WeightGraph object
    val decodedDiGraph = diGraphJson.fromJson[DiGraph[String]]
    val decodedUndirectedGraph = undirectedGraphJson.fromJson[UndirectedGraph[String]]
    val decodedWeightedGraph = weightedGraphJson.fromJson[WeightGraph[String]]

    // Tests d'affichage des graphes
    println("\nTests d'affichage des graphes:")
    println("\nAffichage du DiGraph:")
    decodedDiGraph match
      case Left(error) => println(s"Failed to decode JSON: $error")
      case Right(graph) => graph.printDot(graph)

    println("\nAffichage de l'UndirectedGraph:")
    decodedUndirectedGraph match
      case Left(error) => println(s"Failed to decode JSON: $error")
      case Right(graph) => graph.printDot(graph)

    println("\nAffichage du WeightGraph:")
    decodedWeightedGraph match
      case Left(error) => println(s"Failed to decode JSON: $error")
      case Right(graph) => graph.printDot(graph)

    Console.printLine("exited").exitCode
  }
}