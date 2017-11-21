import sbt.Keys._
import sbt._

object BuildSettings {
  val buildVersion = Dependencies.reactiveMongoVersion

  val filter = { (ms: Seq[(File, String)]) =>
    ms filter {
      case (file, path) =>
        path != "logback.xml" && !path.startsWith("toignore") && !path.startsWith("samples")
    }
  }

  val buildSettings = Seq(
    organization := "org.reactivemongo",
    version := buildVersion,
    scalaVersion := "2.11.12",
    crossScalaVersions := Seq("2.11.12", "2.12.4"),
    javaOptions in test ++= Seq("-Xmx512m", "-XX:MaxPermSize=512m"),
    scalacOptions ++= Seq("-unchecked", "-deprecation"),
    scalacOptions in(Compile, doc) ++= Seq(
      "-unchecked",
      "-deprecation",
      "-diagrams",
      "-implicits",
      "-feature"
    ),
    scalacOptions in(Compile, doc) ++= Opts.doc.title(
      "ReactiveMongo Criteria"
    ),
    scalacOptions in(Compile, doc) ++= Opts.doc.version(buildVersion),
    mappings in(Compile, packageBin) ~= filter,
    mappings in(Compile, packageSrc) ~= filter,
    mappings in(Compile, packageDoc) ~= filter)
}

object Resolvers {
  val typesafe = Seq(
    "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
    "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/")
  val resolversList = typesafe
}

object Dependencies {
  val reactiveMongoVersion = "0.12.7"

  val bson = "org.reactivemongo" %% "reactivemongo-bson" % reactiveMongoVersion

  val bsonmacros = "org.reactivemongo" %% "reactivemongo-bson-macros" % reactiveMongoVersion

  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % "test"
}
