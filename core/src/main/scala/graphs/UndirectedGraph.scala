package graphs

import zio.json._

class UndirectedGraph[V](val adjList: Map[V, Set[(V, V, Option[Int])]]) extends GraphBase[V] {
  def vertices: Set[V] = adjList.keySet

  def edges: Set[(V, V, Option[Int])] = adjList.values.flatten.toSet

  def addEdge(edge: (V, V, Option[Int])): UndirectedGraph[V] = {
    val (v1, v2, weight) = edge
    val edgeReverted = (v2, v1,weight)
    val newAdjList = adjList.updatedWith(v1)(_.map(_ + edge).orElse(Some(Set(edge))))
      .updatedWith(v2)(_.map(_ + edgeReverted).orElse(Some(Set(edgeReverted))))
    UndirectedGraph(newAdjList)
  }

  def addEdge(from: V, to: V): UndirectedGraph[V] = addEdge((from, to, None))

  def removeEdge(v1: V, v2: V): UndirectedGraph[V] = {
    val newAdjList = adjList.updatedWith(v1)(_.map(_.filterNot(_._2 == v2)))
      .updatedWith(v2)(_.map(_.filterNot(_._2 == v1))).filter(_._2.nonEmpty)
    UndirectedGraph(newAdjList)
  }

  def neighbors(v: V): Set[V] = adjList.getOrElse(v, Set.empty).map(_._2)

  override def toDot(graph: GraphBase[V]): String = {
    val sb = new StringBuilder
    var counter = Set.empty[(V, V)]
    sb.append("undirected graph G {\n")
    for ((v1, v2, weight) <- graph.edges) {
      if (!counter.contains((v1, v2)) && !counter.contains((v2, v1))) {
        val label = weight.map(w => s""" [label="$w"]""").getOrElse("")
        sb.append(s"""  "$v1" -- "$v2"$label;\n""")
        counter += ((v1, v2))
      }
    }
    sb.append("}\n")
    sb.toString()
  }
}


object UndirectedGraph {
  def apply[V](adjList: Map[V, Set[(V, V, Option[Int])]]): UndirectedGraph[V] = new UndirectedGraph(adjList)

  def unapply[V](graph: UndirectedGraph[V]): Option[Map[V, Set[(V, V, Option[Int])]]] = Some(graph.adjList)

  implicit def mapDecoder[V: JsonDecoder : JsonFieldDecoder]: JsonDecoder[Map[V, Set[(V, V, Option[Int])]]] =
    JsonDecoder.map[V, Set[(V, V, Option[Int])]]

  implicit def mapEncoder[V: JsonEncoder : JsonFieldEncoder]: JsonEncoder[Map[V, Set[(V, V, Option[Int])]]] =
    JsonEncoder.map[V, Set[(V, V, Option[Int])]]

  implicit def decoder[V: JsonDecoder : JsonFieldDecoder]: JsonDecoder[UndirectedGraph[V]] =
    mapDecoder[V].map(UndirectedGraph(_))

  implicit def encoder[V: JsonEncoder : JsonFieldEncoder]: JsonEncoder[UndirectedGraph[V]] =
    mapEncoder[V].contramap(_.adjList)
}
