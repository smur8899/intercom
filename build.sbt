name := "intercom"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  // Logging
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.1.3",

  // Test
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",

  // JSON parse
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.4",

  // Managed Resources
  "com.jsuereth" %% "scala-arm" % "2.0"

)