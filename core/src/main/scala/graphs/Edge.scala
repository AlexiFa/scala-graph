package graphs

case class Edge[V](from: V, to: V, weight: Option[Int])
