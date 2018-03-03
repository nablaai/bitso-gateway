import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.nablaai",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "bitso-gateway",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.5.9",
      "com.typesafe.akka" %% "akka-stream" % "2.5.9",
      "com.typesafe.akka" %% "akka-http" % "10.0.11",
      "org.apache.kafka" %% "kafka" % "0.8.0",
      "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime,
      "com.typesafe" % "config" % "1.3.1",
      scalaTest % Test
    )
  )

