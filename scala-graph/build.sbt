ThisBuild / scalaVersion     := "3.3.3"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "scala-graph",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.1",
      "dev.zio" %% "zio-test" % "2.1.1" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
