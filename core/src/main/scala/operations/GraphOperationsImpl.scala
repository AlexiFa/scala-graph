package operations

import graphs._
import scala.annotation.tailrec
import scala.collection.mutable

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
        if vis(neighbor) then if (neighbor != parent) true else false
        else isCyclicUtil(neighbor, vertex, vis)
      }
    }

    graph.vertices.exists(node => isCyclicUtil(node, node, Set.empty))
  }

  def BreadthFirstSearch[V](graph: GraphBase[V], start: V): Set[V] = {
    @tailrec
    def bfs(queue: List[V], visited: Set[V]): Set[V] = queue match {
      case Nil => visited
      case vertex :: rest =>
        val newNeighbors = graph.neighbors(vertex).filter(!visited.contains(_))
        bfs(rest ++ newNeighbors, visited ++ newNeighbors)
    }

    bfs(List(start), Set(start))
  }

  def TopologicalSort[V](graph: DiGraph[V]): List[V] = {
    val visited = mutable.Set[V]()
    val stack = mutable.Stack[V]()

    def visit(v: V): Unit = {
      if (!visited(v)) {
        visited += v
        graph.neighbors(v).foreach(visit)
        stack.push(v)
      }
    }

    graph.vertices.foreach(visit)
    stack.toList
  }

  def Dijkstra[V](graph: WeightGraph[V], start: V): Map[V, Int] = {
    if (graph.edges.exists(_._3.exists(_ < 0)))
      throw new IllegalArgumentException("Negative weights are not allowed")

    val distances = mutable.Map(start -> 0)
    val priorityQueue = mutable.PriorityQueue((0, start))(Ordering.by(-_._1))
    val visited = mutable.Set[V]()

    while (priorityQueue.nonEmpty) {
      val (distance, currentVertex) = priorityQueue.dequeue()
      if (!visited(currentVertex)) {
        visited += currentVertex
        graph.adjList.getOrElse(currentVertex, Set.empty).foreach { case (_, neighbor, weight) =>
          val newDistance = distance + weight.getOrElse(1) // weight is 1 if None
          if (newDistance < distances.getOrElse(neighbor, Int.MaxValue)) {
            distances(neighbor) = newDistance
            priorityQueue.enqueue((newDistance, neighbor))
          }
        }
      }
    }

    distances.toMap
  }

  def FloydWarshall[V](graph: WeightGraph[V]): Map[V, Map[V, Long]] = {
    val vertices = graph.vertices.toList
    val inf = Long.MaxValue / 2  // Use Long and divide by 2 to prevent overflow

    // Initialize distances
    def initDist: Map[(V, V), Long] = {
      (for {
        v <- vertices
        u <- vertices
      } yield {
        (v, u) -> (
          if (v == u) 0L
          else graph.edges.find(e => e._1 == v && e._2 == u)
            .flatMap(_._3)
            .map(_.toLong)
            .getOrElse(inf)
          )
      }).toMap
    }

    @tailrec
    def FloydWarshallHelper(k: Int, dist: Map[(V, V), Long]): Map[(V, V), Long] = {
      if (k == vertices.size) dist
      else {
        val newDist = for {
          i <- vertices
          j <- vertices
          throughK = dist.getOrElse((i, vertices(k)), inf) +
            dist.getOrElse((vertices(k), j), inf)
          newDist = math.min(dist.getOrElse((i, j), inf), throughK)
        } yield (i, j) -> newDist

        FloydWarshallHelper(k + 1, dist ++ newDist)
      }
    }

    val finalDist = FloydWarshallHelper(0, initDist)

    // Convert to the required format
    vertices.map { v =>
      v -> vertices.map(u => u -> finalDist.getOrElse((v, u), inf)).toMap
    }.toMap
  }
}
