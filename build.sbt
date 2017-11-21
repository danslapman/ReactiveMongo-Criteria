import BuildSettings._
import Resolvers._
import Dependencies._

lazy val `criteria-dsl` = Project(
  "ReactiveMongo-Criteria-DSL",
  file("."),
  settings = buildSettings ++ Seq(
    resolvers := resolversList,
    libraryDependencies ++= Seq(
      scalaTest, bson, bsonmacros,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )
  )
).enablePlugins(SbtOsgi)

licenses += ("Apache v2", url("http://www.apache.org/licenses/LICENSE-2.0"))

bintrayOrganization := Some("danslapman")

bintrayReleaseOnPublish in ThisBuild := false