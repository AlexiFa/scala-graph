import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import operations.GraphOperationsImpl.CycleDetection
import Utils._

class CycleDetectionSpec extends AnyFlatSpec with Matchers {

  "CycleDetection" should "detect a cycle in a directed graph" in {
    val graph = getDiGraph

    CycleDetection(graph) should be(true)
  }

  it should "not detect a cycle in a directed graph" in {
    val graph = getDiGraph
    val result = CycleDetection(graph.removeEdge("C", "D"))
    
    result should be(false)
  }

  it should "detect a cycle in an undirected graph" in {
    val graph = getUndirectedGraph
    
    CycleDetection(graph) should be(true)
  }

  it should "not detect a cycle in an undirected graph" in {
    val graph = getUndirectedGraph
    val result = CycleDetection(graph.removeEdge("B", "E"))
    
    result should be(false)
  }

  it should "detect a cycle in a weighted graph" in {
    val graph = getWeightedGraph
    
    CycleDetection(graph) should be(true)
  }

  it should "not detect a cycle in a weighted graph" in {
    val graph = getWeightedGraph
    val result = CycleDetection(graph.removeEdge("A", "C").removeEdge("B", "F").removeEdge("D", "E"))
    
    result should be(false)
  }
}
