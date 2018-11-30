package org.vincibean.kafka.playground.consumer

import java.time.Duration
import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConverters._

object TweetConsumer extends App {

  val ZK_HOST = "zookeeper:2181"
  val TOPIC = "tweets"

  private val props = new Properties()
  props.put("group.id", "tweet-consumer")
  props.put("zookeeper.connect", ZK_HOST)
  props.put("auto.offset.reset", "smallest")
  props.put("consumer.timeout.ms", "120000")
  props.put("zookeeper.connection.timeout.ms", "20000")
  props.put("auto.commit.interval.ms", "10000")

  val consumer = new KafkaConsumer[String, String](props)

  consumer.subscribe(java.util.Collections.singletonList(TOPIC))

  while (true) {
    val records = consumer.poll(Duration.ofMillis(100))
    for (record <- records.asScala) {
      val tweet = record.value()
      val numTags = tweet.count(_ == '#')
      println(s"[Consumer] [TagCount=$numTags] $tweet")
      println(record)
    }
  }

}
