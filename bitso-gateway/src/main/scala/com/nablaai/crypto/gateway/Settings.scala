package com.nablaai.crypto.gateway

import com.typesafe.config.{Config, ConfigFactory}

object Settings {

  private val app: Config = ConfigFactory.load().getConfig("application")

  object Bitso {

    private val bitso: Config = app.getConfig("bitso")
    val ws: String = bitso.getString("ws")
    val subscriptionOrdersBtcMxn: String = bitso.getString("subscription_order_btc_mxn")
    val subscriptionTradesBtcMxn: String = bitso.getString("subscription_trades_btc_mxn")
    val subscriptionDiffOrdersBtcMxn: String = bitso.getString("subscription_diff_orders_btc_mxn")

  }

  object Kafka {
    private val kafka: Config = app.getConfig("kafka")
    val boostrapServer: String = kafka.getString("bootstrap_server")
    val stringSerializer: String = kafka.getString("string_serializer")

    object Topics {
      private val topics: Config = kafka.getConfig("topics")
      val bitcoinBitsoTrades: String = topics.getString("bitcoin_bitso_trades")
      val bitcoinBitsoOrders: String = topics.getString("bitcoin_bitso_orders")
      val bitcoinBistoDiffOrders: String = topics.getString("bitcoin_bitso_diff_orders")
    }
  }
}

