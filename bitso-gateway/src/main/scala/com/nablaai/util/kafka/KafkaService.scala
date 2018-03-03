package com.nablaai.util.kafka

import akka.Done
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.concurrent.Future
import java.util.Properties

case class KafkaServiceConfig(bootStrapServers: String, keySerializer: String, valueSerializer: String)

class KafkaService(config: KafkaServiceConfig) {

  KafkaService.setConfiguration(config)

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

  private var producer: KafkaProducer[String, String] = _
  private var configuration: KafkaServiceConfig = _

  def setConfiguration(config: KafkaServiceConfig): Unit = {
    this.configuration = config
    this.producer = null
  }

  def getProducer: KafkaProducer[String, String] = {
    if (this.configuration == null)
      throw new Exception("Set Configuration Required")

    if (this.producer == null){
      val props = new Properties()
      props.put("bootstrap.servers", configuration.bootStrapServers)
      props.put("key.serializer", configuration.keySerializer)
      props.put("value.serializer", configuration.valueSerializer)
      this.producer = new KafkaProducer[String, String](props)
    }
    this.producer
  }
}

