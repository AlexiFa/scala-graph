//import zio.*
//import zio.Console.printLine
//
//object Main extends ZIOAppDefault:
//  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
//    printLine("Welcome to your first ZIO app!")
import zio._

object Main extends ZIOAppDefault {
  def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] = Console.printLine("Hello, World!")
}