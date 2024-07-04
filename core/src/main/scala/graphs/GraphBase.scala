package graphs

trait GraphBase[V, E] {
  def vertices: Set[V]
  def edges: Set[E]
  def addEdge(v1: V, v2: V, weight: Option[Int]): GraphBase[V, E]
  def removeEdge(v1: V, v2: V): GraphBase[V, E]
  def neighbors(v: V): Set[V]
}
