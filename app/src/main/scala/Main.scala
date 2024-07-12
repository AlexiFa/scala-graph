//import zio.*
//import zio.Console.printLine
//
//object Main extends ZIOAppDefault:
//  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
//    printLine("Welcome to your first ZIO app!")
import zio._
import graphs._
//import graphs.GraphVizExtensions._

object Main extends ZIOAppDefault {
  def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] = {
    var graph = UndirectedGraph(Map("A" -> Set.empty, "B" -> Set.empty, "C" -> Set.empty))
    graph = graph.addEdge(("A", "B", None))
    graph = graph.addEdge(("B", "C", None))
//    val e1 = Edge(v1, v2, None)
//    val e2 = Edge(v2, v3, None)
    // val graph = UndiGraph(Map(v1 -> Set(e1), v2 -> Set(e2), v3 -> Set(e2)))
    // val graph = new Map[Vertex, Set[Edge]]
    // val test = Graph(GraphType.Undirected, Map.empty)
    //Console.printLine(graph.toDot).exitCode
      Console.printLine("exited").exitCode
  }
}