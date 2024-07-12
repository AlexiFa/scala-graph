package graphs

case class WeightGraph[V](override val adjList: Map[V, Set[(V, V, Option[Int])]]) extends UndiGraph[V](adjList){
  override def addEdge(edge: (V, V, Option[Int])): WeightGraph[V] = {
    WeightGraph(super.addEdge(edge).adjList)
  }
  override def removeEdge(v1: V, v2: V): WeightGraph[V] = {
    WeightGraph(super.removeEdge(v1, v2).adjList)
  }
}
