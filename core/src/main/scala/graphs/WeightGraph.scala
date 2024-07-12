package graphs

import zio.json._

case class WeightGraph[V](override val adjList: Map[V, Set[(V, V, Option[Int])]]) extends UndirectedGraph[V](adjList) {
  override def addEdge(edge: (V, V, Option[Int])): WeightGraph[V] = {
    WeightGraph(super.addEdge(edge).adjList)
  }

  override def removeEdge(v1: V, v2: V): WeightGraph[V] = {
    WeightGraph(super.removeEdge(v1, v2).adjList)
  }
}

object WeightGraph {
  def apply[V](adjList: Map[V, Set[(V, V, Option[Int])]]): WeightGraph[V] = new WeightGraph(adjList)

  def unapply[V](graph: WeightGraph[V]): Option[Map[V, Set[(V, V, Option[Int])]]] = Some(graph.adjList)

  implicit def mapDecoder[V: JsonDecoder: JsonFieldDecoder]: JsonDecoder[Map[V, Set[(V, V, Option[Int])]]] =
    JsonDecoder.map[V, Set[(V, V, Option[Int])]]

  implicit def mapEncoder[V: JsonEncoder: JsonFieldEncoder]: JsonEncoder[Map[V, Set[(V, V, Option[Int])]]] =
    JsonEncoder.map[V, Set[(V, V, Option[Int])]]

  implicit def decoder[V: JsonDecoder: JsonFieldDecoder]: JsonDecoder[WeightGraph[V]] =
    mapDecoder[V].map(WeightGraph(_))

  implicit def encoder[V: JsonEncoder: JsonFieldEncoder]: JsonEncoder[WeightGraph[V]] =
    mapEncoder[V].contramap(_.adjList)
}
