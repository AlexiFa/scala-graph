import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import operations.GraphOperationsImpl.Dijkstra
import Utils.*

class DijkstraSpec extends AnyFlatSpec with Matchers {

  "Dijkstra" should "find the shortest path from a vertex to all other vertices in a weighted graph" in {
    val graph = getWeightedGraph
    val result = Dijkstra(graph, "A")

    val expectedResult = Map(
      "E" -> (24, List("A", "B", "D", "E")),
      "F" -> (23, List("A", "B", "D", "F")),
      "A" -> (0, List("A")),
      "B" -> (10, List("A", "B")),
      "C" -> (15, List("A", "C")),
      "D" -> (22, List("A", "B", "D"))
    )
    
    result should be(expectedResult)
  }
}
