import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Utils.*
import zio.json.*
import graphs.*
import zio.json.ast.Json

import scala.collection.immutable.HashMap

class JsonSpec extends AnyFlatSpec with Matchers {

  "Implicit decoder" should "decode properly a directed graph" in {
    val diGraphJson = readJsonFromFile("app/src/test/resources/directed_graph.json")
    val graph = diGraphJson.fromJson[DiGraph[String]]

    val expectedResult = createDiGraph

    graph match
      case Left(error) => throw new RuntimeException(s"Failed to decode JSON: $error")
      case Right(result) => result should be(expectedResult)
  }

  it should "decode properly a weighted graph" in {
    val weightGraph = readJsonFromFile("app/src/test/resources/weighted_graph.json")
    val graph = weightGraph.fromJson[WeightGraph[String]]

    val expectedResult = createWeightedGraph

    graph match
    case Left(error) => throw new RuntimeException(s"Failed to decode JSON: $error")
    case Right(result) => result should be(expectedResult)
  }

  "Implicit encoder" should "encode properly a directed graph" in {
    val diGraph = createDiGraph
    val result = diGraph.toJson

    val expectedResult = readJsonFromFile("app/src/test/resources/directed_graph.json")

    result.fromJson[Json] should be(expectedResult.fromJson[Json])
  }

  it should "encode properly a weighted graph" in {
    val weightGraph = createWeightedGraph
    val result = weightGraph.toJson

    val expectedResult = readJsonFromFile("app/src/test/resources/weighted_graph.json")

    result.fromJson[Json] should be(expectedResult.fromJson[Json])
  }

  private def createDiGraph = {
    DiGraph(Map(
      "A" -> Set(("A", "B", None), ("A", "C", None)),
      "B" -> Set(),
      "C" -> Set(("C", "B", None), ("C", "D", None)),
      "D" -> Set(("D", "C", None))
    ))
  }

  private def createWeightedGraph = {
    WeightGraph(HashMap(
      "A" -> Set(("A", "B", Some(10)), ("A", "C", Some(15))),
      "B" -> Set(("B", "A", Some(10)), ("B", "D", Some(12)), ("B", "F", Some(15))),
      "C" -> Set(("C", "A", Some(15)), ("C", "E", Some(10))),
      "D" -> Set(("D", "B", Some(12)), ("D", "E", Some(2)), ("D", "F", Some(1))),
      "E" -> Set(("E", "C", Some(10)), ("E", "D", Some(2)), ("E", "F", Some(5))),
      "F" -> Set(("F", "B", Some(15)), ("F", "D", Some(1)), ("F", "E", Some(5)))
    ))
  }
}
