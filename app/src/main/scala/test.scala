
package graphs

object GraphVizExtensions {
  implicit class VertexOps(vertex: Vertex) {
    def toDot: String = s""""${vertex.name}""""
  }

  implicit class EdgeOps(edge: Edge) {
    def toDot: String = {
      val label = edge.weight.map(w => s""" [label="$w"]""").getOrElse("")
      s""""${edge.from.name}" -> "${edge.to.name}"$label"""
    }
  }

  implicit class DiGraphOps(graph: DiGraph) {
    def toDot: String = {
      val nodesDot = graph.vertices.map(_.toDot).mkString(";\n")
      val edgesDot = graph.edges.map(_.toDot).mkString(";\n")
      s"""digraph G {
         |$edgesDot;
         |}
         |""".stripMargin
    }
  }




}
