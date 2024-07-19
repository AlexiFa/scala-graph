import zio._
import zio.Console._
import graphs._
import operations._
import scala.collection.immutable.HashMap

object GraphApp extends ZIOAppDefault {

  var graph: GraphBase[Any] = DiGraph(Map.empty[Any, Set[(Any, Any, Option[Int])]])

  def addEdge(from: Any, to: Any, weight: Option[Int]): Task[Unit] =
    ZIO.succeed {
      graph = graph.addEdge((from, to, weight)).asInstanceOf[GraphBase[Any]]
    }

  def removeEdge(from: Any, to: Any): Task[Unit] =
    ZIO.succeed {
      graph = graph.removeEdge(from, to).asInstanceOf[GraphBase[Any]]
    }

  def printGraph: Task[Unit] =
    ZIO.succeed(graph.printDot(graph))

  def printVertices: Task[Unit] =
    for {
      _ <- Console.printLine("Vertices:")
      vertices = graph.vertices
      _ <- ZIO.foreach(vertices)(v => Console.printLine(v.toString))
    } yield ()

  def printEdgeAdded(from: Any, to: Any, weight: Option[Int]): Task[Unit] =
    Console.printLine(s"Edge added from $from to $to with weight ${weight.getOrElse("None")}")

  def printEdgeRemoved(from: Any, to: Any): Task[Unit] =
    Console.printLine(s"Edge removed from $from to $to")

  def getUserInput(prompt: String): ZIO[Any, Throwable, String] =
    for {
      _ <- Console.printLine(prompt)
      input <- Console.readLine
    } yield input

  def getOptionalWeight(prompt: String): ZIO[Any, Throwable, Option[Int]] =
    for {
      _ <- Console.printLine(prompt)
      input <- Console.readLine
      weight <- if (input.isEmpty) ZIO.succeed(None) else ZIO.attempt(Some(input.toInt)).orElseFail(new RuntimeException("Invalid input for weight"))
    } yield weight

  def printBanner(message: String): Task[Unit] =
    for {
      _ <- Console.printLine("-" * 65)
      _ <- Console.printLine(s"${" " * ((65 - message.length) / 2)}$message")
      _ <- Console.printLine("-" * 65)
    } yield ()

  def menu: ZIO[Any, Throwable, Unit] =
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
          weight <- getOptionalWeight("Enter the weight (or leave blank for no weight):")
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
        case "6" => ZIO.succeed(())
        case _ => Console.printLine("Invalid choice. Please try again.")
      }
      _ <- if (choice == "6") ZIO.succeed(()) else menu
    } yield ()

  def algorithmMenu: ZIO[Any, Throwable, Unit] =
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
        case "7" => ZIO.succeed(())
        case _ => Console.printLine("Invalid choice. Please try again.")
      }
      _ <- if (choice == "7") menu else algorithmMenu
    } yield ()

  def computeDFS: ZIO[Any, Throwable, Unit] =
    for {
      start <- getUserInput("Enter the starting vertex:")
      result = GraphOperationsImpl.DepthFirstSearch(graph, start)
      _ <- Console.printLine(s"DFS result starting from $start: ${result.mkString(", ")}")
    } yield ()

  def computeBFS: ZIO[Any, Throwable, Unit] =
    for {
      start <- getUserInput("Enter the starting vertex:")
      result = GraphOperationsImpl.BreadthFirstSearch(graph, start)
      _ <- Console.printLine(s"BFS result starting from $start: ${result.mkString(", ")}")
    } yield ()

  def computeTopologicalSort: ZIO[Any, Throwable, Unit] =
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

  def computeCycleDetection: ZIO[Any, Throwable, Unit] ={
    val result = GraphOperationsImpl.CycleDetection(graph)
    if (result) Console.printLine(s"Cycle is found") else Console.printLine(s"There is no cycle")
  }

  def printFloydWarshallMatrix[V](result: Map[V, Map[V, Long]]): Task[Unit] = {
    val vertices = result.keys.toList
    for {
      _ <- Console.printLine("Floyd-Warshall's algorithm result:")
      _ <- Console.printLine("-------------------------------")
      _ <- Console.printLine("   | " + vertices.map(v => f"$v%2s").mkString(" "))
      _ <- Console.printLine("---+" + "-" * (vertices.length * 3 + 1))
      _ <- ZIO.foreach(vertices) { v =>
        for {
          _ <- Console.print(f"$v%2s | ")
          _ <- ZIO.foreach(vertices) { u =>
            Console.print(f"${result(v)(u)}%2d ")
          }
          _ <- Console.printLine("")
        } yield ()
      }
    } yield ()
  }
    


  def computeFloydWarshall: ZIO[Any, Throwable, Unit] =
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


  def printDijkstraResult[V, W](result: Map[V, (W, List[V])]): Task[Unit] = {
    for {
      _ <- Console.printLine("Dijkstra's algorithm result:")
      _ <- Console.printLine("-------------------------------")
      _ <- ZIO.foreach(result.toList) { case (destination, (distance, path)) =>
        for {
          _ <- Console.print(s"To $destination: ")
          _ <- Console.print(s"Distance = $distance, ")
          _ <- Console.printLine(s"Path = ${path.mkString(" -> ")}")
        } yield ()
      }
    } yield ()
  }
    
    
  def computeDijkstra: ZIO[Any, Throwable, Unit] =
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

  def loadGraphFromJson: ZIO[Any, Throwable, Unit] =
  for {
    filePath <- getUserInput("Enter the path to the JSON file:")
    _ <- ZIO.attempt {
      // Ici, vous devrez implémenter la logique pour charger le JSON et créer le graphe
      // Par exemple :
      // graph = JsonLoader.loadGraph(filePath)
      Console.printLine("Graph loaded successfully.")
    }.catchAll { error =>
      Console.printLine(s"Error loading graph: ${error.getMessage}")
    }
  } yield ()


  def initialMenu: ZIO[Any, Throwable, Unit] =
  for {
    _ <- printBanner("Initial Menu:")
    _ <- Console.printLine("1. Create a new graph")
    _ <- Console.printLine("2. Load a graph from JSON")
    _ <- Console.printLine("-" * 65)
    choice <- getUserInput("Enter your choice:")
    _ <- choice match {
      case "1" => menu
      case "2" => loadGraphFromJson
      case _ => Console.printLine("Invalid choice. Please try again.") *> initialMenu
    }
  } yield ()


  def graphTypeMenu: ZIO[Any, Throwable, Unit] =
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

  def run: ZIO[Any, Throwable, Unit] =
    for {
      _ <- printBanner("Welcome to our Graph App in Scala")
      _ <- graphTypeMenu
      _ <- initialMenu
      _ <- menu
    } yield ()
}
