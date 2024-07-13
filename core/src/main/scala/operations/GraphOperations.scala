package operations

import graphs._

trait GraphOperations[V] {
  def DepthFirstSearch(graph: GraphBase[V], start: V): Set[V]
  def CycleDetection(graph: GraphBase[V]): Boolean
}
