package graphs

case class UndiGraph[V](adjList: Map[V, Set[(V, V, Option[Int])]]) extends GraphBase[V]{
  def vertices: Set[V] = adjList.keySet
  def edges: Set[(V, V, Option[Int])] = adjList.values.flatten.toSet
  def addEdge(edge: (V, V, Option[Int])): UndiGraph[V] = {
    val (v1, v2, _) = edge
    val edgeReverted = (v2, v1, None)
    val newAdjList = adjList.updatedWith(v1)(_.map(_ + edge).orElse(Some(Set(edge))))
      .updatedWith(v2)(_.map(_ + edgeReverted).orElse(Some(Set(edgeReverted))))
    UndiGraph(newAdjList)
  }
  def addEdge(from: V, to: V): UndiGraph[V] = addEdge((from, to, None))
  def removeEdge(v1: V, v2: V): UndiGraph[V] = {
    val newAdjList = adjList.updatedWith(v1)(_.map(_.filterNot(_._2 == v2)))
      .updatedWith(v2)(_.map(_.filterNot(_._2 == v1)))
    UndiGraph(newAdjList)
  }
  def neighbors(v: V): Set[V] = adjList.getOrElse(v, Set.empty).map(_._2)
}
