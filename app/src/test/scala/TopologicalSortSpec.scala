import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import operations.GraphOperationsImpl.TopologicalSort
import Utils.*


class TopologicalSortSpec extends AnyFlatSpec with Matchers {

  "TopologicalSort" should "topological sort a directed graph" in {
    val graph = getDiGraph
    val result = TopologicalSort(graph.removeEdge("C", "D"))

    val expectedResult = List("D", "A", "C", "B")

    result should be(expectedResult)
  }
}
