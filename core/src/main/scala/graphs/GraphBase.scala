package graphs

type E[V] = (V, V, Option[Int])

trait GraphBase[V] {
  def vertices: Set[V]
  def edges: Set[(V, V, Option[Int])]
  def addEdge(edge: (V, V, Option[Int])): GraphBase[V]
  def removeEdge(v1: V, v2: V): GraphBase[V]
  def neighbors(v: V): Set[V]
}
