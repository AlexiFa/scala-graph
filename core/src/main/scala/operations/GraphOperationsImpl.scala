package operations
import graphs._

object GraphOperationsImpl extends GraphOperations[Any]{
  def DepthFirstSearch(graph: GraphBase[Any], start: Any, visited: Set[Any]): Set[Any] = {
    if (visited.size == graph.vertices.size) visited
    else {
      val neighbors = graph.neighbors(start)
      val newVisited = visited + start
      neighbors.foldLeft(newVisited)((acc, neighbor) => {
        if (acc.contains(neighbor)) acc
        else DepthFirstSearch(graph, neighbor, acc)
      })
    }
  }
}
