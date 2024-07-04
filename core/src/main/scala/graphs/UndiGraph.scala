package graphs

case class UndiGraph(adjList: Map[Vertex, Set[Edge]]) extends GraphBase[Vertex, Edge]{
  def vertices: Set[Vertex] = adjList.keySet
  def edges: Set[Edge] = adjList.values.flatten.toSet
  def addEdge(v1: Vertex, v2: Vertex, weight: Option[Int]): UndiGraph = {
    val edge = Edge(v1, v2, None)
    val edgeReverted = Edge(v2, v1, None)
    val newAdjList = adjList.updatedWith(v1)(_.map(_ + edge).orElse(Some(Set(edge))))
      .updatedWith(v2)(_.map(_ + edgeReverted).orElse(Some(Set(edgeReverted))))
    UndiGraph(newAdjList)
  }
  def removeEdge(v1: Vertex, v2: Vertex): UndiGraph = {
    val newAdjList = adjList.updatedWith(v1)(_.map(_.filterNot(_.to == v2)))
      .updatedWith(v2)(_.map(_.filterNot(_.to == v1)))
    UndiGraph(newAdjList)
  }
  def neighbors(v: Vertex): Set[Vertex] = adjList.getOrElse(v, Set.empty).map(_.to)
}
