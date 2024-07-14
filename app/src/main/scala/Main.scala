import zio._
import zio.json._
import graphs._
//import graphs.GraphVizExtensions._

object Main extends ZIOAppDefault {
  def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] = {
    val vertexA = "A"
    val vertexB = "B"
    val vertexC = "C"

    val edge1 = (vertexA, vertexB, None)
    val edge2 = (vertexA, vertexC, None)
    val edge3 = (vertexB, vertexC, None)
    val edge1R = (vertexB, vertexA, None)
    val edge3R = (vertexC, vertexB, None)
    val edge1W = (vertexA, vertexB, Some(5))
    val edge2W = (vertexA, vertexC, Some(8))
    val edge3W = (vertexB, vertexC, Some(2))
    val edge1WR = (vertexB, vertexA, Some(5))
    val edge2WR = (vertexC, vertexA, Some(8))
    val edge3WR = (vertexC, vertexB, Some(2))

    val graph = DiGraph(Map(
      vertexA -> Set(edge1, edge2),
      vertexB -> Set(edge3),
      vertexC -> Set.empty
    ))

    val uGraph = UndirectedGraph(Map(
      vertexA -> Set(edge1),
      vertexB -> Set(edge1R, edge3),
      vertexC -> Set(edge3R)
    ))

    val wGraph = WeightGraph(Map(
      vertexA -> Set(edge1W, edge2W),
      vertexB -> Set(edge1WR, edge3W),
      vertexC -> Set(edge2WR, edge3WR)
    ))

    // Tests d'affichage des graphes
    println("\nTests d'affichage des graphes:")
    println("\nAffichage du DiGraph:")
    graph.printDot(graph)

    println("\nAffichage de l'UndirectedGraph:")
    uGraph.printDot(uGraph)

    println("\nAffichage du WeightGraph:")
    wGraph.printDot(wGraph)



    val graphJson = graph.toJson
    println(s"Graph Json: $graphJson")

    val decodedGraph = graphJson.fromJson[DiGraph[String]]
    println(s"Graph: $decodedGraph")

    val uGraphJson = uGraph.toJson
    println(s"UndirectedGraph Json: $uGraphJson")

    val decodedUGraph = uGraphJson.fromJson[UndirectedGraph[String]]
    println(s"UndirectedGraph: $decodedUGraph")

    val wGraphJson = wGraph.toJson
    println(s"WeightedGraph Json: $wGraphJson")

    val decodedWGraph = wGraphJson.fromJson[WeightGraph[String]]
    println(s"WeightedGraph: $decodedWGraph")

    var graph2: WeightGraph[String] =
      WeightGraph(Map("A" -> Set.empty, "B" -> Set.empty, "C" -> Set.empty, "D" -> Set.empty, "E" -> Set.empty, "F" -> Set.empty))
    graph2 = graph2.addEdge("A", "B", 10).addEdge("B", "A", 10)
      .addEdge("A", "C", 15).addEdge("C", "A", 15)
      .addEdge("B", "D", 12).addEdge("D", "B", 12)
      .addEdge("B", "F", 15).addEdge("F", "B", 15)
      .addEdge("C", "E", 10).addEdge("E", "C", 10)
      .addEdge("D", "E", 2).addEdge("E", "D", 2)
      .addEdge("D", "F", 1).addEdge("F", "D", 1)
      .addEdge("F", "E", 5).addEdge("E", "F", 5)

    val graph2json = graph2.toJson
    println(s"WeightedGraph Json: $graph2json")

    Console.printLine("exited").exitCode
  }
}