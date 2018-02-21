package com.nablaai.crypto.gateway

import akka.http.scaladsl.Http
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.http.scaladsl.model.ws.{Message, TextMessage, WebSocketRequest}

import scala.concurrent.Promise


object Main extends Context {

  def main(args: Array[String]): Unit = {

    val flow: Flow[Message, Message, Promise[Option[Message]]] =
    Flow.fromSinkAndSourceMat(
      Sink.foreach[Message](println),
      Source(List(
        TextMessage(Settings.Bitso.subscription_trades),
        TextMessage(Settings.Bitso.subscription_order),
        TextMessage(Settings.Bitso.subscription_diff_orders)))
        .concatMat(Source.maybe[Message])(Keep.right))(Keep.right)

    val (upgradeResponse, promise) =
      Http().singleWebSocketRequest(
        WebSocketRequest(Settings.Bitso.ws),
        flow)


    //promise.success(None)
  }

}

