package graphs

case class WeightGraph(adjList: Map[Vertex, Set[Edge]]) extends GraphBase[Vertex, Edge]{
  def vertices: Set[Vertex] = adjList.keySet
  def edges: Set[Edge] = adjList.values.flatten.toSet
  def addEdge(v1: Vertex, v2: Vertex, weight: Option[Int]): WeightGraph = {
    val edge = Edge(v1, v2, weight)
    val edgeReverted = Edge(v2, v1, weight)
    val newAdjList = adjList.updatedWith(v1)(_.map(_ + edge).orElse(Some(Set(edge))))
      .updatedWith(v2)(_.map(_ + edgeReverted).orElse(Some(Set(edgeReverted))))
    WeightGraph(newAdjList)
  }
  def removeEdge(v1: Vertex, v2: Vertex): WeightGraph = {
    val newAdjList = adjList.updatedWith(v1)(_.map(_.filterNot(_.to == v2)))
    WeightGraph(newAdjList)
  }
  def neighbors(v: Vertex): Set[Vertex] = adjList.getOrElse(v, Set.empty).map(_.to)
}
