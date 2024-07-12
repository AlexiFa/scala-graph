ThisBuild / scalaVersion     := "3.3.3"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"


//lazy val root = (project in file("."))
//  .aggregate(core, app)
//  .settings(
//    name := "scala-graph",
////    libraryDependencies ++= Seq(
////      "dev.zio" %% "zio" % "2.1.1",
////      "dev.zio" %% "zio-test" % "2.1.1" % Test
////    ),
////    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
//  )

lazy val core = (project in file("core"))
  .settings(
    name := "scala-graph-core",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio-json" % "0.6.2",
    )
  )

lazy val app = (project in file("app"))
  .dependsOn(core)
  .settings(
    name := "scala-graph-app",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.5",
      "dev.zio" %% "zio-test" % "2.1.4" % Test,
      "guru.nidi" % "graphviz-java" % "0.18.1"
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
