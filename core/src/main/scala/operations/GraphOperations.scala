package operations

import graphs._

trait GraphOperations {
  def DepthFirstSearch[V](graph: GraphBase[V], start: V): Set[V]

  def CycleDetection[V, G <: GraphBase[V]](graph: G): Boolean

  def BreadthFirstSearch[V](graph: GraphBase[V], start: V): Set[V]

  def TopologicalSort[V](graph: DiGraph[V]): List[V]

  def Dijkstra[V](graph: WeightGraph[V], start: V): Map[V, Int]

  def FloydWarshall[V](graph: WeightGraph[V]): Map[V, Map[V, Long]]
}
