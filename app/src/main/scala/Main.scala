//import zio.*
//import zio.Console.printLine
//
//object Main extends ZIOAppDefault:
//  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
//    printLine("Welcome to your first ZIO app!")
import zio._
import graphs._

object Main extends ZIOAppDefault {
  def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] = {
    val v1 = Vertex("A")
    val v2 = Vertex("B")
    val v3 = Vertex("C")
    var graph = UndiGraph(Map(v1 -> Set.empty, v2 -> Set.empty, v3 -> Set.empty))
    graph = graph.addEdge(v1, v2)
    graph = graph.addEdge(v2, v3)

//    val e1 = Edge(v1, v2, None)
//    val e2 = Edge(v2, v3, None)
    // val graph = UndiGraph(Map(v1 -> Set(e1), v2 -> Set(e2), v3 -> Set(e2)))
    // val graph = new Map[Vertex, Set[Edge]]
    // val test = Graph(GraphType.Undirected, Map.empty)
    Console.printLine("Hello, World!").exitCode
  }
}