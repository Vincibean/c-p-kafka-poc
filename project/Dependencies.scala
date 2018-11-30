import sbt._

object Dependencies {

  lazy val producerDeps: Seq[ModuleID] = twitter ++ kafka

  lazy val consumerDeps: Seq[ModuleID] = kafka

  private lazy val kafka = Seq(
    "org.apache.kafka" %% "kafka" % "2.1.0" excludeAll(
      ExclusionRule("org.slf4j", "slf4j-log4j12"),
      ExclusionRule("javax.jms", "jms"),
      ExclusionRule("com.sun.jdmk", "jmxtools"),
      ExclusionRule("com.sun.jmx", "jmxri")
    )
  )
  
  private lazy val twitter = Seq(
    "org.twitter4j" % "twitter4j-core" % "4.0.4",
    "org.twitter4j" % "twitter4j-stream" % "4.0.4",
  )

}
