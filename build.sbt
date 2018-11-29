import Dependencies._
import Docker._

organization in ThisBuild := "vincibean"

name in ThisBuild := "KafkaTwitterDockerExample"

version in ThisBuild := "1.0"

scalaVersion in ThisBuild := "2.11.8"

lazy val root = (project in file("."))
  .dependsOn(producer, consumer)
  .aggregate(producer, consumer)

lazy val producer = (project in file("tweet-producer"))
  .enablePlugins(DockerPlugin)
  .settings(
    libraryDependencies ++= producerDeps,
    dockerSettings
  )

lazy val consumer = (project in file("tweet-consumer"))
  .enablePlugins(DockerPlugin)
  .settings(
    libraryDependencies ++= consumerDeps,
    dockerSettings
  )