import zio.test.*
import graphs.*
import operations.*

object OperationsSpec extends ZIOSpecDefault{
  def spec =
    suite("CircleDetection")(
      test("DiGraph") {
        var graph: DiGraph[Any] = DiGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty))
        graph = graph.addEdge(0, 1)
        graph = graph.addEdge(0, 2)
        graph = graph.addEdge(1, 2)
        graph = graph.addEdge(1, 3)
        graph = graph.addEdge(3, 3)
        graph = graph.addEdge(2, 0)
        assertTrue(GraphOperationsImpl.CycleDetection(graph))

        graph = graph.removeEdge(3, 3)
        graph = graph.removeEdge(2, 0)
        assertTrue(!GraphOperationsImpl.CycleDetection(graph))
      }//,
//      test("UndirectedGraph") {
//        var graph2: UndirectedGraph[Any] = UndirectedGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty))
//        graph2 = graph2.addEdge(0, 1)
//        graph2 = graph2.addEdge(1, 2)
//        graph2 = graph2.addEdge(2, 3)
//        val res2 = GraphOperationsImpl.CycleDetectionDi(graph2)
//        assertTrue(!res2)
//      }
    )+
    suite("DFS")(
      test("UndirectedGraph") {
        var graph: UndirectedGraph[Any] = UndirectedGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty, 4 -> Set.empty))
        graph = graph.addEdge(0, 1)
        graph = graph.addEdge(0, 2)
        graph = graph.addEdge(0, 3)
        graph = graph.addEdge(2, 3)
        graph = graph.addEdge(2, 4)
        graph = graph.addEdge(3, 2)
        assertTrue(GraphOperationsImpl.DepthFirstSearch(graph, 0) == Set(0, 1, 2, 3, 4))

        graph = graph.removeEdge(2, 4)
        assertTrue(GraphOperationsImpl.DepthFirstSearch(graph, 0) == Set(0, 1, 2, 3))
        assertTrue(GraphOperationsImpl.DepthFirstSearch(graph, 4) == Set(4))
      },
      test("DiGraph") {
        // first digraph
        var graph: DiGraph[Any] = DiGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty))
        graph = graph.addEdge(0, 1)
        graph = graph.addEdge(0, 2)
        graph = graph.addEdge(1, 2)
        graph = graph.addEdge(2, 0)
        graph = graph.addEdge(3, 3)
        graph = graph.addEdge(1, 3)
        val res = GraphOperationsImpl.DepthFirstSearch(graph, 2)
        assertTrue(res == Set(2, 0, 1, 3))

        graph = graph.removeEdge(1, 3)
        graph = graph.addEdge(2, 3)
        val res2 = GraphOperationsImpl.DepthFirstSearch(graph, 2)
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
