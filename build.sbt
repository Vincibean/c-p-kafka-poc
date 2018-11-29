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
    libraryDependencies ++= Seq(
      "org.twitter4j" % "twitter4j-core" % "4.0.4",
      "org.twitter4j" % "twitter4j-stream" % "4.0.4",
      "org.apache.kafka" % "kafka_2.11" % "0.10.0.0" withSources() exclude("org.slf4j", "slf4j-log4j12") exclude("javax.jms", "jms") exclude("com.sun.jdmk", "jmxtools") exclude("com.sun.jmx", "jmxri")
    ),
    dockerSettings
  )

lazy val consumer = (project in file("tweet-consumer"))
  .enablePlugins(DockerPlugin)
  .settings(
    libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.10.0.0" withSources() exclude("org.slf4j", "slf4j-log4j12") exclude("javax.jms", "jms") exclude("com.sun.jdmk", "jmxtools") exclude("com.sun.jmx", "jmxri"),
    dockerSettings
  )

val dockerSettings = Seq(

  dockerfile in docker := {
    val artifact: File = assembly.value
    val artifactTargetPath = s"/app/${artifact.name}"

    new Dockerfile {
      from("openjdk")
      add(artifact, artifactTargetPath)
      entryPoint("java", "-jar", artifactTargetPath)
    }
  },
  imageNames in docker := Seq(
    ImageName(
      namespace = Some(organization.value),
      repository = name.value,
      tag = Some("v" + version.value)
    )
  )
)