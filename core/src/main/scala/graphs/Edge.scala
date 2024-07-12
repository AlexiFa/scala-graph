package graphs

import zio.json._

case class Edge[V](from: V, to: V, weight: Option[Int])

object Edge {
  implicit def decoder[V: JsonDecoder]: JsonDecoder[Edge[V]] = DeriveJsonDecoder.gen[Edge[V]]

  implicit def encoder[V: JsonEncoder]: JsonEncoder[Edge[V]] = DeriveJsonEncoder.gen[Edge[V]]
}
