package operations

import graphs._

trait GraphOperations[V] {
  def DepthFirstSearch(graph: GraphBase[V], start: V): Set[V]
  def CycleDetection[G <: GraphBase[V]](graph: G): Boolean
  def BreadthFirstSearch(graph: GraphBase[V], start: V): Set[V]
  def TopologicalSort(graph: DiGraph[V]): Set[V]
  def Dijkstra[V](graph: WeightGraph[V], start: V): Map[V, Int]
  def FloydWarshall(graph: WeightGraph[V]): Map[V, Map[V, Int]]
}
