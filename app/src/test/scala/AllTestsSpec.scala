import zio.test._

object AllTestsSpec extends ZIOSpecDefault {
  def spec =
    suite("AllTestsSpec")(
      CircleDetectionSpec.spec,
      DepthFirstSearchSpec.spec,
      DijkstraSpec.spec
    )
}
