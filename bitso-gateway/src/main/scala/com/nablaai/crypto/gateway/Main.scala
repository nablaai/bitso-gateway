package com.nablaai.crypto.gateway

import akka.Done
import akka.http.scaladsl.Http
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.http.scaladsl.model.ws.{Message, TextMessage, WebSocketRequest}

import com.nablaai.util.kafka.{KafkaService, KafkaServiceConfig}

import scala.concurrent.{Future, Promise}

object Main extends Context {

  def main(args: Array[String]): Unit = {

    val kafkaServiceConfig: KafkaServiceConfig =
      KafkaServiceConfig(Settings.Kafka.boostrapServer, Settings.Kafka.stringSerializer, Settings.Kafka.stringSerializer)

    val kafkaService: KafkaService = new KafkaService(kafkaServiceConfig)

    val bitsoBitcoinSource: Source[Message, Promise[Option[Message]]] = Source(List(
      TextMessage(Settings.Bitso.subscriptionTradesBtcMxn),
      TextMessage(Settings.Bitso.subscriptionOrdersBtcMxn),
      TextMessage(Settings.Bitso.subscriptionDiffOrdersBtcMxn)))
      .concatMat(Source.maybe[Message])(Keep.right)

    val kafkaSink: Sink[Message, Future[Done]] = {
      Sink.foreach[Message]{
        case message: TextMessage.Strict => {
          message.text match {
            case subscription if subscription contains "subscribe" =>
              println(subscription)
            case diffOrder if diffOrder contains "diff-orders" =>
              kafkaService.publishElement("key", Settings.Kafka.Topics.bitcoinBistoDiffOrders, diffOrder)
            case order if order contains "orders" =>
              kafkaService.publishElement("key", Settings.Kafka.Topics.bitcoinBitsoOrders, order)
            case trade if trade contains "trades" =>
              kafkaService.publishElement("key", Settings.Kafka.Topics.bitcoinBitsoTrades, trade)
            case _ => None
          }
        }
        case anything => println(anything)
      }
    }

    val webSocketFlow: Flow[Message, Message, Promise[Option[Message]]] =
      Flow.fromSinkAndSourceMat(kafkaSink, bitsoBitcoinSource)(Keep.right)

    val (upgradeResponse, promise) =
      Http().singleWebSocketRequest(WebSocketRequest(Settings.Bitso.ws), webSocketFlow)

    upgradeResponse.onComplete(println)

  }
}

