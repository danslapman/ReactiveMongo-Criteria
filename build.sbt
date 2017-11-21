import BuildSettings._
import Resolvers._
import Dependencies._

lazy val dsl = Project(
  "ReactiveMongo-Criteria-DSL",
  file("."),
  settings = buildSettings ++ osgiSettings ++ Seq(
    resolvers := resolversList,
    OsgiKeys.exportPackage := Seq(
      "reactivemongo.extensions.dsl.criteria"
    ),
    libraryDependencies ++= Seq(
      scalaTest, bson, bsonmacros,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )
  )
).enablePlugins(SbtOsgi)