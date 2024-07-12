import zio.test._
import graphs._
import operations._

object DepthFirstSearchSpec extends ZIOSpecDefault{
  def spec =
    suite("spec")(
      test("UndirectedGraph") {
        var graph: UndirectedGraph[Any] = UndirectedGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty, 4 -> Set.empty))
        graph = graph.addEdge(0, 1)
        graph = graph.addEdge(0, 2)
        graph = graph.addEdge(0, 3)
        graph = graph.addEdge(2, 3)
        graph = graph.addEdge(2, 4)
        graph = graph.addEdge(3, 2)
        val res = GraphOperationsImpl.DepthFirstSearch(graph, 0)
        assertTrue(res == Set(0, 1, 2, 3, 4))
      },
      test("DiGraph") {
        // first digraph
        var graph: DiGraph[Any] = DiGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty))
        graph = graph.addEdge(0, 1)
        graph = graph.addEdge(0, 2)
        graph = graph.addEdge(1, 2)
        graph = graph.addEdge(1, 3)
        graph = graph.addEdge(2, 0)
        graph = graph.addEdge(3, 3)
        val res = GraphOperationsImpl.DepthFirstSearch(graph, 2)
        assertTrue(res == Set(2, 0, 1, 3))
        // second digraph
        var graph2: DiGraph[Any] = DiGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty))
        graph2 = graph2.addEdge(0, 1)
        graph2 = graph2.addEdge(0, 2)
        graph2 = graph2.addEdge(1, 2)
        graph2 = graph2.addEdge(2, 3)
        graph2 = graph2.addEdge(2, 0)
        graph2 = graph2.addEdge(3, 3)
        val res2 = GraphOperationsImpl.DepthFirstSearch(graph2, 1)
        assertTrue(res2 == Set(1, 2, 0, 3))
      },
      test("WeightGraph") {
        var weightGraph: WeightGraph[Any] = WeightGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty, 4 -> Set.empty))
        weightGraph = weightGraph.addEdge(0, 1, 1)
        weightGraph = weightGraph.addEdge(0, 2, 2)
        weightGraph = weightGraph.addEdge(0, 3, 3)
        weightGraph = weightGraph.addEdge(2, 3, 4)
        weightGraph = weightGraph.addEdge(2, 4, 5)
        weightGraph = weightGraph.addEdge(3, 2, 6)
        val res = GraphOperationsImpl.DepthFirstSearch(weightGraph, 0)
        assertTrue(res == Set(0, 1, 2, 3, 4))
      }
    )
}
