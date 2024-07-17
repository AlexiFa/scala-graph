import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import operations.GraphOperationsImpl.Dijkstra
import Utils.*

class DijkstraSpec extends AnyFlatSpec with Matchers {

  it should "find the shortest path from a vertex to all other vertices in a weighted graph" in {
    val graph = getWeightedGraph
    true should be(Dijkstra(graph, "A") == Map("A" -> 0, "B" -> 10, "C" -> 15, "D" -> 22, "E" -> 24, "F" -> 23))
  }

  //  def spec =
  //    test("Dijkstra") {
  //      val inputFile = "app/src/test/resources/weighted_graph.json"
  //
  //      // Read the JSON content from the file
  //      val jsonContent = Source.fromFile(inputFile).mkString
  //
  //      // Parse the JSON content to create a WeightGraph object
  //      val decodedGraph = jsonContent.fromJson[WeightGraph[String]]
  //
  //      // Handle the Either result from fromJson
  //      decodedGraph match {
  //        case Left(error) =>
  //          assertNever(s"Failed to decode JSON: $error")
  //        case Right(graph) =>
  //          // Proceed with the graph object
  //          val res = GraphOperationsImpl.Dijkstra(graph, "A")
  //          assertTrue(res == Map("A" -> 0, "B" -> 10, "C" -> 15, "D" -> 22, "E" -> 24, "F" -> 23))
  //      }
  //    }
}
