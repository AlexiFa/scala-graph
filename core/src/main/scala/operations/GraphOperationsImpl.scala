package operations

import graphs._
import scala.annotation.tailrec

object GraphOperationsImpl extends GraphOperations {
  def DepthFirstSearch[V](graph: GraphBase[V], start: V): Set[V] = {
    @tailrec
    def dfs(stack: List[V], visited: Set[V]): Set[V] = {
      stack match {
        case Nil => visited
        // if head is in visited, skip it
        case head :: tail =>
          if (visited.contains(head)) {
            dfs(tail, visited)
            // if head is not in visited, get neighbors of head
          } else {
            // get neighbors of head that have not been visited
            val neighbors = graph.neighbors(head).filterNot(visited)
            // add head to visited and add neighbors to stack
            dfs(neighbors.toList ::: tail, visited + head)
          }
      }
    }

    dfs(List(start), visited = Set.empty)
  }

  def CycleDetection[V, G <: GraphBase[V]](graph: G): Boolean = {
    graph match {
      case _: DiGraph[V] => CycleDetectionDi(graph.asInstanceOf[DiGraph[V]])
      case _: UndirectedGraph[V] => CycleDetectionUndirected(graph.asInstanceOf[UndirectedGraph[V]])
    }
  }

  private def CycleDetectionDi[V](graph: DiGraph[V]): Boolean = {
    var visited: Map[V, Boolean] = Map().withDefaultValue(false)
    var recStack: Map[V, Boolean] = Map().withDefaultValue(false)
    def isCyclicUtil(vertex: V): Boolean = {
      visited += (vertex -> true)
      recStack += (vertex -> true)

      val hasCycle = graph.neighbors(vertex).exists { neighbor =>
        if (!visited(neighbor)) {
          isCyclicUtil(neighbor)
        } else {
          recStack(neighbor)
        }
      }
      recStack += (vertex -> false)
      hasCycle
    }
    graph.vertices.exists(node => !visited(node) && isCyclicUtil(node))
  }

  private def CycleDetectionUndirected[V](graph: UndirectedGraph[V]): Boolean = {
    def isCyclicUtil(vertex: V, parent: V, visited: Set[V]): Boolean = {
      val vis = visited.+(vertex)
      graph.neighbors(vertex).exists { neighbor =>
        if (vis(neighbor)) then if (neighbor != parent) true else false
        else isCyclicUtil(neighbor, vertex, vis)
      }
    }
    graph.vertices.exists(node => isCyclicUtil(node, node, Set.empty))
  }
  
  def BreadthFirstSearch[V](graph: GraphBase[V], start: V): Set[V] = ???
  
  def TopologicalSort[V](graph: DiGraph[V]): Set[V] = ???
  
  def Dijkstra[V](graph: WeightGraph[V], start: V): Map[V, Int] = {
    if graph.edges.exists { case (_, _, weight) => weight.exists(_ < 0) } then throw new IllegalArgumentException("Negative weights are not allowed")
    Map.empty
  }
  
  def FloydWarshall[V](graph: WeightGraph[V]): Map[V, Map[V, Int]] = ???
}
