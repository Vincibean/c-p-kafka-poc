lazy val root = (project in file("."))
  .settings(
    name := "KafkaTwitterDockerExample",
    version := "1.0",
    scalaVersion := "2.11.8"
  )

lazy val producer = (project in file("tweet-producer"))
  .enablePlugins(sbtdocker.DockerPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "org.twitter4j" % "twitter4j-core" % "4.0.4",
      "org.twitter4j" % "twitter4j-stream" % "4.0.4",
      "org.apache.kafka" % "kafka_2.11" % "0.10.0.0" withSources() exclude("org.slf4j", "slf4j-log4j12") exclude("javax.jms", "jms") exclude("com.sun.jdmk", "jmxtools") exclude("com.sun.jmx", "jmxri")
    ),
    dockerSettings()
  )

lazy val consumer = (project in file("tweet-consumer"))
  .enablePlugins(sbtdocker.DockerPlugin)
  .settings(
    libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.10.0.0" withSources() exclude("org.slf4j", "slf4j-log4j12") exclude("javax.jms", "jms") exclude("com.sun.jdmk", "jmxtools") exclude("com.sun.jmx", "jmxri"),
    dockerSettings()
  )

def dockerSettings() = Seq(

  dockerfile in docker := {
    val artifactSource: File = assembly.value
    val artifactTargetPath = s"/project/${artifactSource.name}"
    val scriptSourceDir = baseDirectory.value / "../scripts"
    val projectDir = "/project/"

    new Dockerfile {
      from("openjdk")
      add(artifactSource, artifactTargetPath)
      copy(scriptSourceDir, projectDir)
      entryPoint(s"/project/start.sh")
      cmd(projectDir, s"${name.value}", s"${version.value}")
    }
  },
  imageNames in docker := Seq(
    ImageName(s"vincibean/${name.value}:latest")
  )
)