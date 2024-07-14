import graphs.{DiGraph, UndirectedGraph, WeightGraph}

var graph: DiGraph[Any] = DiGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty))
graph = graph.addEdge(0, 1)
graph = graph.addEdge(0, 2)
graph = graph.addEdge(1, 2)
graph = graph.addEdge(1, 3)
//graph = graph.addEdge(3, 3)
//graph = graph.addEdge(2, 0)

val res = operations.GraphOperationsImpl.CycleDetection(graph)

var graph2: UndirectedGraph[Any] = UndirectedGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty))
graph2 = graph2.addEdge(0, 1)
graph2 = graph2.addEdge(0, 2)
//graph2 = graph2.addEdge(1, 2)
graph2 = graph2.addEdge(2, 3)
//graph2 = graph2.addEdge(2, 0)
//graph2 = graph2.addEdge(3, 3)

val res2 = operations.GraphOperationsImpl.CycleDetection(graph2)

var graph3: WeightGraph[Any] = WeightGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty))
graph3 = graph3.addEdge(0, 1, 1)
graph3 = graph3.addEdge(0, 2, 2)
//graph3 = graph3.addEdge(1, 2, 3)
graph3 = graph3.addEdge(1, 3, 4)
//graph3 = graph3.addEdge(2, 0, 5)
//graph3 = graph3.addEdge(3, 3, 6)

val res3 = operations.GraphOperationsImpl.CycleDetection(graph3)