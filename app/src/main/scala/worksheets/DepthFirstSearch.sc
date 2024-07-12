import graphs.UndirectedGraph

var graph: UndirectedGraph[Any] = UndirectedGraph(Map(0 -> Set.empty, 1 -> Set.empty, 2 -> Set.empty, 3 -> Set.empty, 4 -> Set.empty))
graph = graph.addEdge(0, 1)
graph = graph.addEdge(0, 2)
graph = graph.addEdge(0, 3)
graph = graph.addEdge(2, 3)
graph = graph.addEdge(2, 4)
graph = graph.addEdge(3, 2)

val res = operations.GraphOperationsImpl.DepthFirstSearch(graph, 0, Set.empty)