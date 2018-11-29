import sbt._

object Dependencies {

  lazy val producerDeps: Seq[ModuleID] = Seq(
    "org.twitter4j" % "twitter4j-core" % "4.0.4",
    "org.twitter4j" % "twitter4j-stream" % "4.0.4",
    "org.apache.kafka" % "kafka_2.11" % "0.10.0.0" withSources() exclude("org.slf4j", "slf4j-log4j12") exclude("javax.jms", "jms") exclude("com.sun.jdmk", "jmxtools") exclude("com.sun.jmx", "jmxri")
  )
  
  lazy val consumerDeps: Seq[ModuleID] = Seq(
    "org.apache.kafka" % "kafka_2.11" % "0.10.0.0" withSources() exclude("org.slf4j", "slf4j-log4j12") exclude("javax.jms", "jms") exclude("com.sun.jdmk", "jmxtools") exclude("com.sun.jmx", "jmxri")
  )
  
}
