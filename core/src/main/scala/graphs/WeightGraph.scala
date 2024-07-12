package graphs
// TODO: faire que le weighted grph extends undigraph
case class WeightGraph[V](adjList: Map[V, Set[(V, V, Option[Int])]]) extends GraphBase[V]{
  def vertices: Set[V] = adjList.keySet 
  def edges: Set[(V, V, Option[Int])] = adjList.values.flatten.toSet
  def addEdge(edge: (V, V, Option[Int])): WeightGraph[V] = {
    val (v1, v2, weight) = edge
    val edgeReverted = (v2, v1, weight)
    val newAdjList = adjList.updatedWith(v1)(_.map(_ + edge).orElse(Some(Set(edge))))
      .updatedWith(v2)(_.map(_ + edgeReverted).orElse(Some(Set(edgeReverted))))
    WeightGraph(newAdjList)
  }
  def removeEdge(v1: V, v2: V): WeightGraph[V] = {
    val newAdjList = adjList.updatedWith(v1)(_.map(_.filterNot(_._2 == v2)))
    WeightGraph(newAdjList)
  }
  def neighbors(v: V): Set[V] = adjList.getOrElse(v, Set.empty).map(_._2)
}
