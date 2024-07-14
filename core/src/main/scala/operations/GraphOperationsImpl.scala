package operations

import graphs._
import scala.annotation.tailrec

object GraphOperationsImpl extends GraphOperations[Any] {
  def DepthFirstSearch(graph: GraphBase[Any], start: Any): Set[Any] = {
    @tailrec
    def dfs(stack: List[Any], visited: Set[Any]): Set[Any] = {
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

  def CycleDetection[G <: GraphBase[Any]](graph: G): Boolean = {
    graph match {
      case _: DiGraph[Any] => CycleDetectionDi(graph.asInstanceOf[DiGraph[Any]])
      case _: UndirectedGraph[Any] => CycleDetectionUndirected(graph.asInstanceOf[UndirectedGraph[Any]])
    }
  }

  private def CycleDetectionDi(graph: DiGraph[Any]): Boolean = {
    var visited: Map[Any, Boolean] = Map().withDefaultValue(false)
    var recStack: Map[Any, Boolean] = Map().withDefaultValue(false)
    def isCyclicUtil(vertex: Any): Boolean = {
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

  private def CycleDetectionUndirected(graph: UndirectedGraph[Any]): Boolean = {
    def isCyclicUtil(vertex: Any, parent: Any, visited: Set[Any]): Boolean = {
      val vis = visited.+(vertex)
      graph.neighbors(vertex).exists { neighbor =>
        if (vis(neighbor)) then if (neighbor != parent) true else false
        else isCyclicUtil(neighbor, vertex, vis)
      }
    }
    graph.vertices.exists(node => isCyclicUtil(node, null, Set.empty))
  }
  
  def BreadthFirstSearch(graph: GraphBase[Any], start: Any): Set[Any] = ???
  
  def TopologicalSort(graph: DiGraph[Any]): Set[Any] = ???
  
  def Dijkstra(graph: WeightGraph[Any], start: Any): Map[Any, Int] = ???
  
  def FloydWarshall(graph: WeightGraph[Any]): Map[Any, Map[Any, Int]] = ???
}
