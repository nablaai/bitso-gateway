package com.nablaai.crypto.gateway

import com.typesafe.config.{Config, ConfigFactory}

object Settings {

  private val app: Config = ConfigFactory.load().getConfig("application")

  object Bitso {

    private val bitso: Config = app.getConfig("bitso")
    val ws: String = bitso.getString("ws")
    val subscription_order: String = bitso.getString("subscription_order")
    val subscription_trades: String = bitso.getString("subscription_trades")
    val subscription_diff_orders: String = bitso.getString("subscription_diff_orders")

  }

}
