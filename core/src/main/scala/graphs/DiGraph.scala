package graphs

import zio.json._

case class DiGraph[V](adjList: Map[V, Set[(V, V, Option[Int])]]) extends GraphBase[V] {
  def vertices: Set[V] = adjList.keySet

  def edges: Set[(V, V, Option[Int])] = adjList.values.flatten.toSet

  def addEdge(edge: (V, V, Option[Int])): DiGraph[V] = {
    val (v1, v2, _) = edge
    val newAdjList = adjList.updatedWith(v1)(_.map(_ + edge).orElse(Some(Set(edge))))
    .updatedWith(v2)(existing => existing.orElse(Some(Set.empty))).filter(_._2.nonEmpty)
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
  def apply[V](adjList: Map[V, Set[(V, V, Option[Int])]]): DiGraph[V] = new DiGraph(adjList)

  def unapply[V](graph: DiGraph[V]): Option[Map[V, Set[(V, V, Option[Int])]]] = Some(graph.adjList)

  implicit def mapDecoder[V: JsonDecoder : JsonFieldDecoder]: JsonDecoder[Map[V, Set[(V, V, Option[Int])]]] =
    JsonDecoder.map[V, Set[(V, V, Option[Int])]]

  implicit def mapEncoder[V: JsonEncoder : JsonFieldEncoder]: JsonEncoder[Map[V, Set[(V, V, Option[Int])]]] =
    JsonEncoder.map[V, Set[(V, V, Option[Int])]]

  implicit def decoder[V: JsonDecoder : JsonFieldDecoder]: JsonDecoder[DiGraph[V]] =
    mapDecoder[V].map(DiGraph(_))

  implicit def encoder[V: JsonEncoder : JsonFieldEncoder]: JsonEncoder[DiGraph[V]] =
    mapEncoder[V].contramap(_.adjList)
}
