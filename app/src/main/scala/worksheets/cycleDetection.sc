import graphs.{DiGraph, UndirectedGraph}

var graph: DiGraph[Any] = DiGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty))
graph = graph.addEdge(0, 1)
graph = graph.addEdge(0, 2)
graph = graph.addEdge(1, 2)
graph = graph.addEdge(1, 3)
//graph = graph.addEdge(3, 3)
//graph = graph.addEdge(2, 0)

val res = operations.GraphOperationsImpl.CycleDetection(graph)
//val neig = graph.neighbors(1)

var graph2: UndirectedGraph[Any] = UndirectedGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty))
graph2 = graph2.addEdge(0, 1)
graph2 = graph2.addEdge(0, 2)
//graph2 = graph2.addEdge(1, 2)
graph2 = graph2.addEdge(2, 3)
//graph2 = graph2.addEdge(2, 0)
//graph2 = graph2.addEdge(3, 3)

//val res2 = operations.GraphOperationsImpl.CycleDetectionDi(graph2)