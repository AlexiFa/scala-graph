package graphs

case class DiGraph[V](adjList: Map[V, Set[(V, V, Option[Int])]]) extends GraphBase[V]{
  def vertices: Set[V] = adjList.keySet
  def edges: Set[(V, V, Option[Int])] = adjList.values.flatten.toSet
  def addEdge(edge: (V, V, Option[Int])): DiGraph[V] = {
    val (v1, v2, _) = edge
    val newAdjList = adjList.updatedWith(v1)(_.map(_ + edge).orElse(Some(Set(edge))))
    DiGraph(newAdjList)
  }
  def addEdge(from: V, to: V): DiGraph[V] = addEdge((from, to, None))
  def removeEdge(v1: V, v2: V): DiGraph[V] = {
    val newAdjList = adjList.updatedWith(v1)(_.map(_.filterNot(_._2 == v2)))
    DiGraph(newAdjList)
  }
  def neighbors(v: V): Set[V] = adjList.getOrElse(v, Set.empty).map(_._2)
}

object DiGraph {
  implicit val mapDecoder: JsonDecoder[Map[Vertex, Set[Edge]]] = JsonDecoder.map[String, Set[Edge]].map(_.map {
    case (k, v) => Vertex(k) -> v
  })

  implicit val mapEncoder: JsonEncoder[Map[Vertex, Set[Edge]]] = JsonEncoder.map[String, Set[Edge]].contramap(_.map {
    case (k, v) => k.name -> v
  })

  implicit val decoder: JsonDecoder[DiGraph] = mapDecoder.map(DiGraph(_))
  implicit val encoder: JsonEncoder[DiGraph] = mapEncoder.contramap(_.adjList)
}
