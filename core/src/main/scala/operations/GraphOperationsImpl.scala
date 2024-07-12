package operations
import graphs._
import scala.annotation.tailrec

object GraphOperationsImpl extends GraphOperations[Any]{
  def DepthFirstSearch(graph: GraphBase[Any], start: Any): Set[Any] = {
    @tailrec
    def dfs(stack: List[Any], visited: Set[Any]): Set[Any] = {
      stack match {
        case Nil => visited
        // if head is in visited, skip it
        case head :: tail =>
          if (visited.contains(head)) {
            dfs(tail, visited)
            // if head is not in visited, get neighbors of head
          } else {
            // get neighbors of head that have not been visited
            val neighbors = graph.neighbors(head).filterNot(visited)
            // add head to visited and add neighbors to stack
            dfs(neighbors.toList ::: tail, visited + head)
          }
      }
    }
    dfs(List(start), visited=Set.empty)
  }
}
