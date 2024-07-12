package operations
import graphs._

trait GraphOperations[V] {
  def DepthFirstSearch(graph: GraphBase[V], start: V, visited: Set[V]): Set[V]
}
