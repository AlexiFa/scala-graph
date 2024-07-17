import zio.json.*
import graphs.*

import scala.io.Source

object Utils {
  def getDiGraph: DiGraph[String] = {
    val inputFile = "app/src/test/resources/directed_graph.json"
    val jsonContent = readJsonFromFile(inputFile)

    // Parse the JSON content to create a WeightGraph object
    val decodedGraph = jsonContent.fromJson[DiGraph[String]]
    decodedGraph match
      case Left(error) => throw new RuntimeException(s"Failed to decode JSON: $error")
      case Right(graph) => graph
  }

  def getUndirectedGraph: UndirectedGraph[String] = {
    val inputFile = "app/src/test/resources/undirected_graph.json"
    val jsonContent = readJsonFromFile(inputFile)

    // Parse the JSON content to create a WeightGraph object
    val decodedGraph = jsonContent.fromJson[UndirectedGraph[String]]
    decodedGraph match
      case Left(error) => throw new RuntimeException(s"Failed to decode JSON: $error")
      case Right(graph) => graph
  }

  def getWeightedGraph: WeightGraph[String] = {
    val inputFile = "app/src/test/resources/weighted_graph.json"
    val jsonContent = readJsonFromFile(inputFile)

    // Parse the JSON content to create a WeightGraph object
    val decodedGraph = jsonContent.fromJson[WeightGraph[String]]
    decodedGraph match
      case Left(error) => throw new RuntimeException(s"Failed to decode JSON: $error")
      case Right(graph) => graph
  }

  private def readJsonFromFile(filePath: String): String = {
    val source = Source.fromFile(filePath)
    try {
      source.mkString
    } finally {
      source.close()
    }
  }
}
