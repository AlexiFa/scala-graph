
package graphs

object GraphVizExtensions {
  implicit class VertexOps(vertex: Vertex) {
    def toDot: String = s""""${vertex.name}""""
  }

  implicit class EdgeOps(edge: Edge) {
    def toDot(graphType: String): String = {
        val edgeSymbol = edge.weight match {
            case Some(_) => "--"  // If there is a weight, treat as undirected
            case None =>
                graphType match {
                    case "graphs.DiGraph" => "->"
                    case "graphs.UndiGraph" => "--"
                }
        }
        
        val weightLabel = edge.weight.map(w => s""" [Weight="$w"]""").getOrElse("")
        s""""${edge.from.name}" $edgeSymbol "${edge.to.name}"$weightLabel"""
    }
}
  implicit class DiGraphOps(graph: GraphBase[Vertex,Edge]) {
    def toDot: String = {
      val nodesDot = graph.vertices.map(_.toDot).mkString(";\n")
      val graphType = graph.getClass().getName()
      val edgesDot = graph.edges.map(_.toDot(graphType)).mkString(";\n")
      s"""digraph G {
         |$edgesDot;
         |}
         |""".stripMargin
    }
  }
}
