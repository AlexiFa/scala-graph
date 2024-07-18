import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import operations.GraphOperationsImpl.FloydWarshall
import Utils.*

class FloydWarshallSpec extends AnyFlatSpec with Matchers {

  "FloydWarshall" should "correctly compute shortest paths for all pairs" in {
    val graph = getWeightedGraph
    val result = FloydWarshall(graph)

    val expectedResult = Map(
      "E" -> Map("E" -> 0L, "F" -> 3L, "A" -> 24L, "B" -> 14L, "C" -> 10L, "D" -> 2L),
      "F" -> Map("E" -> 3L, "F" -> 0L, "A" -> 23L, "B" -> 13L, "C" -> 13L, "D" -> 1L),
      "A" -> Map("E" -> 24L, "F" -> 23L, "A" -> 0L, "B" -> 10L, "C" -> 15L, "D" -> 22L),
      "B" -> Map("E" -> 14L, "F" -> 13L, "A" -> 10L, "B" -> 0L, "C" -> 24L, "D" -> 12L),
      "C" -> Map("E" -> 10L, "F" -> 13L, "A" -> 15L, "B" -> 24L, "C" -> 0L, "D" -> 12L),
      "D" -> Map("E" -> 2L, "F" -> 1L, "A" -> 22L, "B" -> 12L, "C" -> 12L, "D" -> 0L)
    )

    result should be (expectedResult)
  }
}
