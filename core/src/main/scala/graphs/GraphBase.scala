package graphs

trait GraphBase[V] {
  def vertices: Set[V]

  def edges: Set[(V, V, Option[Int])]

  def addEdge(edge: (V, V, Option[Int])): GraphBase[V]

  def removeEdge(v1: V, v2: V): GraphBase[V]

  def neighbors(v: V): Set[V]

  def toDot(graph: GraphBase[V]): String = {
    val sb = new StringBuilder
    sb.append("digraph G {\n")
    for ((v1, v2, weight) <- graph.edges) {
      val label = weight.map(w => s""" [label="$w"]""").getOrElse("")
      sb.append(s"""  "$v1" -> "$v2"$label;\n""")
    }
    sb.append("}\n")
    sb.toString()
  }

  def printDot(graph: GraphBase[V]): Unit = {
    val dotString = toDot(graph)
    println(dotString)
  }
}
