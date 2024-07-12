package graphs

case class WeightGraph[V](override val adjList: Map[V, Set[(V, V, Option[Int])]]) extends UndirectedGraph[V](adjList){
  override def addEdge(edge: (V, V, Option[Int])): WeightGraph[V] = {
    WeightGraph(super.addEdge(edge).adjList)
  }
  def addEdge(from: V, to: V, weight: Int): WeightGraph[V] = addEdge((from, to, Some(weight)))
  override def removeEdge(v1: V, v2: V): WeightGraph[V] = {
    WeightGraph(super.removeEdge(v1, v2).adjList)
  }
}
