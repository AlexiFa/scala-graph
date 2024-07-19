import zio.*
import zio.Console.*
import zio.json.*
import graphs.*
import operations.*

import scala.collection.immutable.HashMap
import scala.io.Source

object GraphApp extends ZIOAppDefault {

  var graph: GraphBase[Any] = DiGraph(Map.empty[Any, Set[(Any, Any, Option[Int])]])

  def addEdge(from: Any, to: Any, weight: Option[Int]): Task[Unit] = {
    ZIO.succeed {
      graph = graph.addEdge((from, to, weight))
    }
  }

  def removeEdge(from: Any, to: Any): Task[Unit] = {
    ZIO.succeed {
      graph = graph.removeEdge(from, to)
    }
  }

  private def printGraph: Task[Unit] = {
    ZIO.succeed(graph.printDot(graph))
  }

  private def printVertices: Task[Unit] = {
    for {
      _ <- Console.printLine("Vertices:")
      vertices = graph.vertices
      _ <- ZIO.foreachDiscard(vertices)(v => Console.printLine(v.toString))
    } yield ()
  }

  private def printEdgeAdded(from: Any, to: Any, weight: Option[Int]): Task[Unit] = {
    Console.printLine(s"Edge added from $from to $to with weight ${weight.getOrElse("None")}")
  }

  private def printEdgeRemoved(from: Any, to: Any): Task[Unit] = {
    Console.printLine(s"Edge removed from $from to $to")
  }

  private def getUserInput(prompt: String): ZIO[Any, Throwable, String] = {
    for {
      _ <- Console.printLine(prompt)
      input <- Console.readLine
    } yield input
  }

  private def getOptionalWeight(prompt: String): ZIO[Any, Throwable, Option[Int]] = {
    if (!graph.isInstanceOf[WeightGraph[_]]) ZIO.none
    else
      for {
        _ <- Console.printLine(prompt)
        input <- Console.readLine
        weight <- if (input.isEmpty) ZIO.none else ZIO.attempt(Some(input.toInt)).orElseFail(new RuntimeException("Invalid input for weight"))
      } yield weight
  }

  private def printBanner(message: String): Task[Unit] = {
    for {
      _ <- Console.printLine("-" * 65)
      _ <- Console.printLine(s"${" " * ((65 - message.length) / 2)}$message")
      _ <- Console.printLine("-" * 65)
    } yield ()
  }

  private def menu: ZIO[Any, Throwable, Unit] = {
    for {
      _ <- printBanner("Menu:")
      _ <- Console.printLine("1. Add edge")
      _ <- Console.printLine("2. Remove edge")
      _ <- Console.printLine("3. Display all vertices")
      _ <- Console.printLine("4. Print graph")
      _ <- Console.printLine("5. Compute algorithms")
      _ <- Console.printLine("6. Exit")
      _ <- Console.printLine("-" * 65)
      choice <- getUserInput("Enter your choice:")
      _ <- choice match {
        case "1" => for {
          from <- getUserInput("Enter the source vertex:")
          to <- getUserInput("Enter the destination vertex:")
          weight <- getOptionalWeight("Enter the weight:")
          _ <- addEdge(from, to, weight)
          _ <- printEdgeAdded(from, to, weight)
        } yield ()
        case "2" => for {
          from <- getUserInput("Enter the source vertex:")
          to <- getUserInput("Enter the destination vertex:")
          _ <- removeEdge(from, to)
          _ <- printEdgeRemoved(from, to)
        } yield ()
        case "3" => printVertices
        case "4" => printGraph
        case "5" => algorithmMenu
        case "6" => ZIO.unit
        case _ => Console.printLine("Invalid choice. Please try again.")
      }
      _ <- ZIO.unless(choice == "6")(menu)
    } yield ()
  }

  private def algorithmMenu: ZIO[Any, Throwable, Unit] = {
    for {
      _ <- printBanner("Compute Algorithms Menu:")
      _ <- Console.printLine("1. Depth-first search")
      _ <- Console.printLine("2. Breadth-first search")
      _ <- Console.printLine("3. Topological sort")
      _ <- Console.printLine("4. Cycle detection")
      _ <- Console.printLine("5. Floyd's algorithm")
      _ <- Console.printLine("6. Dijkstra's algorithm")
      _ <- Console.printLine("7. Back to main menu")
      _ <- Console.printLine("-" * 65)
      choice <- getUserInput("Enter your choice:")
      _ <- choice match {
        case "1" => computeDFS
        case "2" => computeBFS
        case "3" => computeTopologicalSort
        case "4" => computeCycleDetection
        case "5" => computeFloydWarshall
        case "6" => computeDijkstra
        case "7" => ZIO.unit
        case _ => Console.printLine("Invalid choice. Please try again.")
      }
      _ <- algorithmMenu.unless(choice == "7")
    } yield ()
  }

  private def computeDFS: ZIO[Any, Throwable, Unit] = {
    for {
      start <- getUserInput("Enter the starting vertex:")
      result = GraphOperationsImpl.DepthFirstSearch(graph, start)
      _ <- Console.printLine(s"DFS result starting from $start: ${result.mkString(", ")}")
    } yield ()
  }

  private def computeBFS: ZIO[Any, Throwable, Unit] = {
    for {
      start <- getUserInput("Enter the starting vertex:")
      result = GraphOperationsImpl.BreadthFirstSearch(graph, start)
      _ <- Console.printLine(s"BFS result starting from $start: ${result.mkString(", ")}")
    } yield ()
  }

  private def computeTopologicalSort: ZIO[Any, Throwable, Unit] = {
    for {
      _ <- Console.printLine("Topological Sort :")
      result <- graph match {
        case diGraph: DiGraph[Any] =>
          for {
            sortResult <- ZIO.attempt(GraphOperationsImpl.TopologicalSort(diGraph))
            _ <- Console.printLine(s" ${sortResult.toString}")
          } yield ()
        case _ =>
          Console.printLine("This algorithm is only available for directed graphs (DiGraph).")
      }
    } yield ()
  }

  private def computeCycleDetection: ZIO[Any, Throwable, Unit] = {
    val result = GraphOperationsImpl.CycleDetection(graph)
    if (result) Console.printLine(s"Cycle is found") else Console.printLine(s"There is no cycle")
  }

  private def printFloydWarshallMatrix[V](result: Map[V, Map[V, Long]]): Task[Unit] = {
    val vertices = result.keys.toList
    for {
      _ <- Console.printLine("Floyd-Warshall's algorithm result:")
      _ <- Console.printLine("-------------------------------")
      _ <- Console.printLine("   | " + vertices.map(v => f"$v%2s").mkString(" "))
      _ <- Console.printLine("---+" + "-" * (vertices.length * 3 + 1))
      _ <- ZIO.foreachDiscard(vertices) {
        v =>
          for {
            _ <- Console.print(f"$v%2s | ")
            _ <- ZIO.foreachDiscard(vertices) {
              u =>
                Console.print(f"${result(v)(u)}%2d ")
            }
            _ <- Console.printLine("")
          } yield ()
      }
    } yield ()
  }

  private def computeFloydWarshall: ZIO[Any, Throwable, Unit] = {
    for {
      _ <- Console.printLine("Floyd-Warshall Algorithm :")
      result <- graph match {
        case weightGraph: WeightGraph[Any] =>
          for {
            fwResult <- ZIO.attempt(GraphOperationsImpl.FloydWarshall(weightGraph))
            _ <- printFloydWarshallMatrix(fwResult)
          } yield ()
        case _ =>
          Console.printLine("This algorithm is only available for weighted graphs (WeightGraph).")
      }
    } yield ()
  }

  private def printDijkstraResult[V, W](result: Map[V, (W, List[V])]): Task[Unit] = {
    for {
      _ <- Console.printLine("Dijkstra's algorithm result:")
      _ <- Console.printLine("-------------------------------")
      _ <- ZIO.foreachDiscard(result.toList) { case (destination, (distance, path)) =>
        for {
          _ <- Console.print(s"To $destination: ")
          _ <- Console.print(s"Distance = $distance, ")
          _ <- Console.printLine(s"Path = ${path.mkString(" -> ")}")
        } yield ()
      }
    } yield ()
  }

  private def computeDijkstra: ZIO[Any, Throwable, Unit] = {
    for {
      _ <- Console.printLine("Dijkstra's Algorithm:")
      result <- graph match {
        case weightGraph: WeightGraph[Any] =>
          for {
            start <- getUserInput("Enter the starting vertex:")
            dijkstraResult <- ZIO.attempt(GraphOperationsImpl.Dijkstra(weightGraph, start))
            _ <- printDijkstraResult(dijkstraResult)
          } yield ()
        case _ =>
          Console.printLine("This algorithm is only available for weighted graphs (WeightGraph).")
      }
    } yield ()
  }

  private def loadGraphFromJson: ZIO[Any, Throwable, Unit] = {
    for {
      filePath <- getUserInput("Enter the path to the JSON file:")
      _ <- ZIO.attempt {
        val source = Source.fromFile(filePath)
        val jsonString = source.mkString
        source.close()

        // Decode the JSON based on the current type of `graph`
        graph match {
          case _: DiGraph[Any] =>
            val decodedGraph = jsonString.fromJson[DiGraph[String]].getOrElse(throw new IllegalArgumentException("Invalid JSON for DiGraph"))
            graph = decodedGraph.asInstanceOf[DiGraph[Any]]
          case _: UndirectedGraph[Any] =>
            val decodedGraph = jsonString.fromJson[UndirectedGraph[String]].getOrElse(throw new IllegalArgumentException("Invalid JSON for UndirectedGraph"))
            graph = decodedGraph.asInstanceOf[UndirectedGraph[Any]]
          case _ =>
            val decodedGraph = jsonString.fromJson[WeightGraph[String]].getOrElse(throw new IllegalArgumentException("Invalid JSON for WeightGraph"))
            graph = decodedGraph.asInstanceOf[WeightGraph[Any]]
        }

        Console.printLine("Graph loaded successfully.")
      }.catchAll { error =>
        Console.printLine(s"Error loading graph: ${error.getMessage}") *> loadGraphFromJson
      }
    } yield ()
  }

  private def initialMenu: ZIO[Any, Throwable, Unit] = {
    for {
      _ <- printBanner("Initial Menu:")
      _ <- Console.printLine("1. Create a new graph")
      _ <- Console.printLine("2. Load a graph from JSON")
      _ <- Console.printLine("-" * 65)
      choice <- getUserInput("Enter your choice:")
      _ <- choice match {
        case "1" => ZIO.unit
        case "2" => loadGraphFromJson
        case _ => Console.printLine("Invalid choice. Please try again.") *> initialMenu
      }
    } yield ()
  }

  private def graphTypeMenu: ZIO[Any, Throwable, Unit] = {
    for {
      _ <- printBanner("Choose graph type:")
      _ <- Console.printLine("1. Directed Graph")
      _ <- Console.printLine("2. Undirected Graph")
      _ <- Console.printLine("3. Weighted Graph")
      _ <- Console.printLine("-" * 65)
      choice <- getUserInput("Enter your choice:")
      _ <- choice match {
        case "1" => ZIO.succeed {
          graph = DiGraph(Map.empty[Any, Set[(Any, Any, Option[Int])]])
        }
        case "2" => ZIO.succeed {
          graph = UndirectedGraph(Map.empty[Any, Set[(Any, Any, Option[Int])]])
        }
        case "3" => ZIO.succeed {
          graph = WeightGraph(Map.empty[Any, Set[(Any, Any, Option[Int])]])
        }
        case _ => Console.printLine("Invalid choice. Please try again.") *> graphTypeMenu
      }
    } yield ()
  }

  def run: ZIO[Any, Throwable, Unit] =
    for {
      _ <- printBanner("Welcome to our Graph App in Scala")
      _ <- graphTypeMenu
      _ <- initialMenu
      _ <- menu
    } yield ()
}
