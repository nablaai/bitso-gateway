package com.nablaai.util.kafka

import akka.Done
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.concurrent.Future
import java.util.Properties

case class KafkaServiceConfig(bootStrapServers: String, keySerializer: String, valueSerializer: String)

class KafkaService() {

  def publishSink[T](key: String, topic: String): Sink[T, Future[Done]] = {
    Sink.foreach[T](element => {
      val record: ProducerRecord[String, String] = new ProducerRecord[String, String](topic, key, element.toString)
      KafkaService.getProducer.send(record)
    })
  }

  def publishElement(key: String, topic: String, element: String): Unit = {
    val record: ProducerRecord[String, String] = new ProducerRecord[String, String](topic, key, element)
    KafkaService.getProducer.send(record)
  }
}

object KafkaService {

  private val configuration: KafkaServiceConfig =
    defineSerializers(Settings.Kafka.stringSerializer, Settings.Kafka.stringSerializer)

  private val properties: Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", configuration.bootStrapServers)
    props.put("key.serializer", configuration.keySerializer)
    props.put("value.serializer", configuration.valueSerializer)
    props
  }

  private val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](properties)

  def defineSerializers(key: String, value: String): KafkaServiceConfig = {
    KafkaServiceConfig(Settings.Kafka.boostrapServer, key, value)
  }

  def getProducer: KafkaProducer[String, String] = producer
}

