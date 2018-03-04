package com.nablaai.util.kafka

import com.typesafe.config.{Config, ConfigFactory}

object Settings {

  private val app: Config = ConfigFactory.load().getConfig("application")

  object Kafka {
    private val kafka: Config = app.getConfig("kafka")
    val boostrapServer: String = kafka.getString("bootstrap_server")
    val stringSerializer: String = kafka.getString("string_serializer")
  }
}

