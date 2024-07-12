package graphs

case class Edge[V](from: V, to: V, weight: Option[Int])

object Edge {
  implicit val decoder: JsonDecoder[Edge] = DeriveJsonDecoder.gen[Edge]
  implicit val encoder: JsonEncoder[Edge] = DeriveJsonEncoder.gen[Edge]
}
