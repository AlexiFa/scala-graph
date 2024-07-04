package graphs

case class DiGraph(adjList: Map[Vertex, Set[Edge]]) extends GraphBase[Vertex, Edge]{
  def vertices: Set[Vertex] = adjList.keySet
  def edges: Set[Edge] = adjList.values.flatten.toSet
  def addEdge(v1: Vertex, v2: Vertex): DiGraph = {
    val edge = Edge(v1, v2, None)
    val newAdjList = adjList.updatedWith(v1)(_.map(_ + edge).orElse(Some(Set(edge))))
    DiGraph(newAdjList)
  }
  def removeEdge(v1: Vertex, v2: Vertex): DiGraph = {
    val newAdjList = adjList.updatedWith(v1)(_.map(_.filterNot(_.to == v2)))
    DiGraph(newAdjList)
  }
  def neighbors(v: Vertex): Set[Vertex] = adjList.getOrElse(v, Set.empty).map(_.to)
}
