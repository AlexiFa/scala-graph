package graphs

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class Vertex(name: String)

object Vertex {
  implicit val decoder: JsonDecoder[Vertex] = JsonDecoder.string.map(Vertex(_))
  implicit val encoder: JsonEncoder[Vertex] = JsonEncoder.string.contramap(_.name)
}
