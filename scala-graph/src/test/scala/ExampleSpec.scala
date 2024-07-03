import zio.test._

object ExampleSpec extends ZIOSpecDefault {
  def spec =
    suite("spec")(
      test("test1") {
        assertTrue(true)
      },
      test("test2") {
        assertTrue(true)
      }
    )
}
