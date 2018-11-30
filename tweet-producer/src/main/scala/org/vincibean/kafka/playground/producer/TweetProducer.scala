package org.vincibean.kafka.playground.producer

import java.util.Properties

import com.danielasfregola.twitter4s.{TwitterRestClient, TwitterStreamingClient}
import com.danielasfregola.twitter4s.entities.Tweet
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object TweetProducer extends App {

  val BROKER_LIST = "kafka:9092"
  val KEYWORDS = List("#scala", "#kafka", "#cassandra", "#solr", "#apachespark", "#fastdata", "#bigdata")
  val TOPIC = "tweets"

  val props = new Properties()
  props.put("bootstrap.servers", BROKER_LIST)
  props.put("client.id", "KafkaTweetProducer")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val restClient = TwitterRestClient()
  val streamingClient = TwitterStreamingClient()

  streamingClient.sampleStatuses(tracks = KEYWORDS){
    case tweet: Tweet =>
      val tt = tweet.text
      println("[Producer] " + tt)
      val data = new ProducerRecord[String, String](TOPIC, tt)
      producer.send(data)
  }

}
