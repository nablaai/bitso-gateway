package com.vegaai.crypto.gateway;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


public class Configuration {

    private static final Config application = ConfigFactory.load().getConfig("application");

    public static class Bitso {
        private static final Config bitsoConfig = application.getConfig("bitso");
        private static final String ws = bitsoConfig.getString("ws");
        private static final String subscriptionToOrders = bitsoConfig.getString("subscription_order");
        private static final String subscriptionToTrades = bitsoConfig.getString("subscription_trades");
        private static final String subscriptionToDiffOrders = bitsoConfig.getString("subscription_diff_orders");

        public static String getWs() {
            return ws;
        }

        public static String getSubscriptionToOrders() {
            return subscriptionToOrders;
        }

        public static String getSubscriptionToTrades() {
            return subscriptionToTrades;
        }

        public static String getSubscriptionToDiffOrders() {
            return subscriptionToDiffOrders;
        }
    }
}
