import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import operations.GraphOperationsImpl.DepthFirstSearch
import Utils._

class DepthFirstSearchSpec extends AnyFlatSpec with Matchers {

  "DepthFirstSearch" should "traverse all the vertices of a directed graph" in {
    val graph = getDiGraph
    val result = DepthFirstSearch(graph, "A")
    
    val expectedResult = Set("A", "B", "C", "D")
    
    result should be(expectedResult)
  }

  it should "traverse all the vertices of an undirected graph" in {
    val graph = getUndirectedGraph
    val result = DepthFirstSearch(graph, "A")
    
    val expectedResult = Set("A", "B", "C", "D", "E")
    
    result should be(expectedResult)
  }

  it should "traverse all the vertices of a weighted graph" in {
    val graph = getWeightedGraph
    val result = DepthFirstSearch(graph, "A")
    
    val expectedResult = Set("A", "B", "C", "D", "E", "F")
    
    result should be(expectedResult)
  }
}
