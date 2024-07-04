package graphs

import zio.json._

sealed trait GraphType

object GraphType {
  case object Directed extends GraphType

  case object Undirected extends GraphType

  implicit val decoder: JsonDecoder[GraphType] = DeriveJsonDecoder.gen[GraphType]
  implicit val encoder: JsonEncoder[GraphType] = DeriveJsonEncoder.gen[GraphType]
}