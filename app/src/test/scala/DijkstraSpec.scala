import zio.test.*
import graphs.*
import operations.*
import zio.Scope
import zio.json._
import scala.io.Source

object DijkstraSpec extends ZIOSpecDefault {

  def spec =
    test("Dijkstra") {
      val inputFile = "app/src/test/resources/weighted_graph.json"

      // Read the JSON content from the file
      val jsonContent = Source.fromFile(inputFile).mkString

      // Parse the JSON content to create a WeightGraph object
      val decodedGraph = jsonContent.fromJson[WeightGraph[String]]

      // Handle the Either result from fromJson
      decodedGraph match {
        case Left(error) =>
          assertNever(s"Failed to decode JSON: $error")
        case Right(graph) =>
          // Proceed with the graph object
          val res = GraphOperationsImpl.Dijkstra(graph, "A")
          assertTrue(res == Map("A" -> 0, "B" -> 10, "C" -> 15, "D" -> 22, "E" -> 24, "F" -> 23))
      }
    }
}
