package graphs

import zio.json._

case class Vertex(name: String)

object Vertex {
  implicit val decoder: JsonDecoder[Vertex] = JsonDecoder.string.map(Vertex(_))
  implicit val encoder: JsonEncoder[Vertex] = JsonEncoder.string.contramap(_.name)
}
