import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import operations.GraphOperationsImpl.CycleDetection
import Utils._

class CycleDetectionSpec extends AnyFlatSpec with Matchers {

  it should "detect a cycle in a directed graph" in {
    val graph = getDiGraph
    true should be(CycleDetection(graph))
  }

  it should "not detect a cycle in a directed graph" in {
    val graph = getDiGraph
    true should be(!CycleDetection(graph.removeEdge("C", "D")))
  }

  it should "detect a cycle in an undirected graph" in {
    val graph = getUndirectedGraph
    true should be(CycleDetection(graph))
  }

  it should "not detect a cycle in an undirected graph" in {
    val graph = getUndirectedGraph
    true should be(!CycleDetection(graph.removeEdge("B", "E")))
  }

  it should "detect a cycle in a weighted graph" in {
    val graph = getWeightedGraph
    true should be(CycleDetection(graph))
  }

  it should "not detect a cycle in a weighted graph" in {
    val graph = getWeightedGraph
    true should be(!CycleDetection(
      graph.removeEdge("A", "C").removeEdge("B", "F").removeEdge("D", "E")
    ))
  }
}
