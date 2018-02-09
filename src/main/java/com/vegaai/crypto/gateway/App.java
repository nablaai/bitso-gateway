package com.vegaai.crypto.gateway;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.ws.WebSocket;
import org.asynchttpclient.ws.WebSocketListener;
import org.asynchttpclient.ws.WebSocketUpgradeHandler;

public class App {
    public static void main(String[] args) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException {

        AsyncHttpClient client = new DefaultAsyncHttpClient();

        WebSocket webSocket = client.prepareGet(Configuration.Bitso.getWs())
                .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(
                        new WebSocketListener() {

                            public void onOpen(WebSocket webSocket) {
                                System.out.println("Open Websocket");
                                webSocket.sendTextFrame(Configuration.Bitso.getSubscriptionToOrders());
                                webSocket.sendTextFrame(Configuration.Bitso.getSubscriptionToTrades());
                                webSocket.sendTextFrame(Configuration.Bitso.getSubscriptionToDiffOrders());
                            }


                            public void onClose(WebSocket webSocket, int i, String s) {
                                System.out.println("Close Websocket");
                            }


                            public void onError(Throwable throwable) {

                            }

                            public void onTextFrame(String payload, boolean finalFragment, int rsv) {
                                System.out.println(payload);
                            }
                        }
                ).build()).get();
    }
}
