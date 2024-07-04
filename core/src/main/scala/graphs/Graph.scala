package graphs

import zio.json._

case class Graph(graphType: GraphType, adjacencyList: Map[Vertex, Set[Edge]]) {

  private def createEdge(vertex: Vertex, newEdge: Edge) = {
    adjacencyList.get(vertex) match {
      case Some(edges) => edges + newEdge
      case None => Set(newEdge)
    }
  }
  
  def addEdge(from: Vertex, to: Vertex, weight: Option[Int] = None): Graph = {
    val newEdge = Edge(to, weight)
    val updateEdges = createEdge(from, newEdge)
    val updateList = adjacencyList + (from -> updateEdges)

    graphType match {
      case GraphType.Undirected =>
        val reverseEdge = Edge(from, weight)
        val updateReverseEdges = createEdge(to, reverseEdge)
        Graph(graphType, updateList + (to -> updateReverseEdges))
      case GraphType.Directed =>
        Graph(graphType, updateList)
    }
  }

  def removeEdge(from: Vertex, to: Vertex): Graph = {
    val updatedEdges = adjacencyList.get(from).map(_.filterNot(_.destination == to)).getOrElse(Set.empty)
    val updatedList = adjacencyList + (from -> updatedEdges)

    graphType match {
      case GraphType.Undirected =>
        val updatedReverseEdges = adjacencyList.get(to).map(_.filterNot(_.destination == from)).getOrElse(Set.empty)
        Graph(graphType, updatedList + (to -> updatedReverseEdges))
      case GraphType.Directed =>
        Graph(graphType, updatedList)
    }
  }
}

object Graph {

}
