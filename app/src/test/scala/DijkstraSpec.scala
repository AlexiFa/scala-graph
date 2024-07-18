import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import operations.GraphOperationsImpl.Dijkstra
import Utils.*

class DijkstraSpec extends AnyFlatSpec with Matchers {

  "Dijkstra" should "find the shortest path from a vertex to all other vertices in a weighted graph" in {
    val graph = getWeightedGraph
    val result = Dijkstra(graph, "A")
    
    val expectedResult = Map("A" -> 0, "B" -> 10, "C" -> 15, "D" -> 22, "E" -> 24, "F" -> 23)
    
    result should be(expectedResult)
  }
}
