import graphs.UndirectedGraph

var graph: UndirectedGraph[Any] = UndirectedGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty, 4 -> Set.empty))
graph = graph.addEdge(0, 1)
graph = graph.addEdge(0, 2)
graph = graph.addEdge(0, 3)
graph = graph.addEdge(2, 3)
graph = graph.addEdge(2, 4)
graph = graph.addEdge(3, 2)

val res = operations.GraphOperationsImpl.DepthFirstSearch(graph, 0, Set.empty)

var weightGraph: graphs.WeightGraph[Any] = graphs.WeightGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty, 4 -> Set.empty))

weightGraph = weightGraph.addEdge(0, 1, 1)
weightGraph = weightGraph.addEdge(0, 2, 2)
weightGraph = weightGraph.addEdge(0, 3, 3)
weightGraph = weightGraph.addEdge(2, 3, 4)
weightGraph = weightGraph.addEdge(2, 4, 5)
weightGraph = weightGraph.addEdge(3, 2, 6)

val res2 = operations.GraphOperationsImpl.DepthFirstSearch(weightGraph, 0, Set.empty)