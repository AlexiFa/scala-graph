package graphs

import zio.json._

case class Vertex(id: String, data: Map[String, String])

object Vertex {
  implicit val decoder: JsonDecoder[Vertex] = DeriveJsonDecoder.gen[Vertex]
  implicit val encoder: JsonEncoder[Vertex] = DeriveJsonEncoder.gen[Vertex]
}
