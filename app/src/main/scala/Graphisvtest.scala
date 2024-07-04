// Define basic graph structures
case class Node(id: String)
case class Edge(from: Node, to: Node)
case class Graph(nodes: Set[Node], edges: Set[Edge])

// Implicit extensions for generating DOT representation
object GraphVizExtensions {
  implicit class NodeOps(node: Node) {
    def toDot: String = s""""${node.id}""""
  }

  implicit class EdgeOps(edge: Edge) {
    def toDot: String = s""""${edge.from.id}" -> "${edge.to.id}""""
  }

  implicit class GraphOps(graph: Graph) {
    def toDot: String = {
      val nodesDot = graph.nodes.map(_.toDot).mkString(";\n")
      val edgesDot = graph.edges.map(_.toDot).mkString(";\n")
      s"""digraph G {
         |$nodesDot;
         |$edgesDot;
         |}
         |""".stripMargin
    }
  }
}

// Example usage
object GraphVizExample {
  import GraphVizExtensions._

  def main(args: Array[String]): Unit = {
    val nodeA = Node("A")
    val nodeB = Node("B")
    val nodeC = Node("C")

    val edgeAB = Edge(nodeA, nodeB)
    val edgeBC = Edge(nodeB, nodeC)
    val edgeCA = Edge(nodeC, nodeA)

    val graph = Graph(Set(nodeA, nodeB, nodeC), Set(edgeAB, edgeBC, edgeCA))

    println(graph.toDot)
  }
}
