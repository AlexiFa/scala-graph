package graphs

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class Edge(destination: Vertex, weight: Option[Int])

object Edge {
  implicit val decoder: JsonDecoder[Edge] = DeriveJsonDecoder.gen[Edge]
  implicit val encoder: JsonEncoder[Edge] = DeriveJsonEncoder.gen[Edge]
}
