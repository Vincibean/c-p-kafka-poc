import Dependencies._
import Docker._

organization in ThisBuild := "vincibean"

scalaVersion in ThisBuild := "2.12.7"

lazy val root = (project in file("."))
  .settings(
    name := "Apache Kafka Playground"
  )
  .dependsOn(producer, consumer)
  .aggregate(producer, consumer)

lazy val producer = (project in file("tweet-producer"))
  .enablePlugins(DockerPlugin)
  .settings(
    name := "tweet-producer-app",
    version := "1.0",
    libraryDependencies ++= producerDeps,
    dockerSettings
  )

lazy val consumer = (project in file("tweet-consumer"))
  .enablePlugins(DockerPlugin)
  .settings(
    name := "tweet-consumer-app",
    version := "1.0",
    libraryDependencies ++= consumerDeps,
    dockerSettings
  )