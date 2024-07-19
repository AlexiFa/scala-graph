import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import graphs.DiGraph
import Utils.*

class GraphSpec extends AnyFlatSpec with Matchers {

  "Add edge" should "correctly add edges to the graph" in {
    val graph = DiGraph(Map.empty[Any, Set[(Any, Any, Option[Int])]])
    val result = graph.addEdge("A", "B")
      .addEdge("B", "C")
      .addEdge("A", "C")

    val expectedResult = DiGraph(Map(
      "A" -> Set(("A", "B", None), ("A", "C", None)),
      "B" -> Set(("B", "C", None)),
      "C" -> Set()
    ))

    result should be(expectedResult)
  }

  "Remove edge" should "correctly remove edges from the graph" in {
    val graph = getDiGraph
    val result = graph.removeEdge("D", "C")
      .removeEdge("C", "B")

    val expectedResult = DiGraph(Map(
      "A" -> Set(("A", "B", None), ("A", "C", None)),
      "B" -> Set(),
      "C" -> Set(("C", "D", None)),
      "D" -> Set()
    ))

    result should be(expectedResult)
  }

  it should "correctly remove vertices from the graph" in {
    val graph = getDiGraph
    val result = graph.removeEdge("D", "C")
      .removeEdge("C", "D")

    val expectedResult = DiGraph(Map(
      "A" -> Set(("A", "B", None), ("A", "C", None)),
      "B" -> Set(),
      "C" -> Set(("C", "B", None))
    ))

    result should be(expectedResult)
  }
}
