import graphs.*
import operations.GraphOperationsImpl
import zio.test.*
import zio.json.*

import scala.io.Source

object DepthFirstSearchSpec extends ZIOSpecDefault {

  def spec =
    suite("DFS")(
      test("on_directed_graph") {
        val inputFile = "app/src/test/resources/directed_graph.json"

        // Read the JSON content from the file
        val jsonContent = Source.fromFile(inputFile).mkString

        // Parse the JSON content to create a WeightGraph object
        val decodedGraph = jsonContent.fromJson[DiGraph[String]]

        // Handle the Either result from fromJson
        decodedGraph match {
          case Left(error) =>
            assertNever(s"Failed to decode JSON: $error")
          case Right(graph) =>
            // Proceed with the graph object
            assertTrue(GraphOperationsImpl.DepthFirstSearch(graph, "A") == Set("A", "B", "C", "D"))
        }
      },
      test("on_undirected_graph") {
        val inputFile = "app/src/test/resources/undirected_graph.json"

        // Read the JSON content from the file
        val jsonContent = Source.fromFile(inputFile).mkString

        // Parse the JSON content to create a WeightGraph object
        val decodedGraph = jsonContent.fromJson[UndirectedGraph[String]]

        // Handle the Either result from fromJson
        decodedGraph match {
          case Left(error) =>
            assertNever(s"Failed to decode JSON: $error")
          case Right(graph) =>
            // Proceed with the graph object
            assertTrue(GraphOperationsImpl.DepthFirstSearch(graph, "A") == Set("A", "B", "C", "D", "E"))
        }
      },
      test("on_weighted_graph") {
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
            assertTrue(GraphOperationsImpl.DepthFirstSearch(graph, "A") == Set("A", "B", "C", "D", "E", "F"))
        }
      }
    )
}
