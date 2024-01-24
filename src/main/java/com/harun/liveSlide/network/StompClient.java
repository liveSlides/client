package com.harun.liveSlide.network;

import com.harun.liveSlide.exceptions.SessionIsNotConnectedException;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.messaging.simp.stomp.StompSession.Subscription;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

public class StompClient {
    private static final String URL = "ws://18.197.102.102:8080/liveSlideSocket";
    private static WebSocketStompClient stompClient;
    private static StompSession stompSession;
    private static Map<String, Class<?>> topicTypeMap = new HashMap<>();
    private static Map<String, Consumer<?>> topicHandlerMap = new HashMap<>();
    private static Map<String, Subscription> activeSubscriptions = new HashMap<>();

    private StompClient() {
    }

    public static void initialize() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(1024*1024*10);
        container.setDefaultMaxBinaryMessageBufferSize(1024*1024*10);
        WebSocketClient wsClient = new StandardWebSocketClient(container);
        stompClient = new WebSocketStompClient(wsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setInboundMessageSizeLimit(1024*1024*1024);
    }

    public static void connect(Consumer<Throwable> errorCallback) throws Exception {
        stompClient.connect(URL, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("Connected to STOMP WebSocket server");
                stompSession = session;

                // Subscribe to each topic in the map
                topicTypeMap.forEach((destination, payloadTypeClass) -> {
                    @SuppressWarnings("unchecked")
                    Consumer<Object> handler = (Consumer<Object>) topicHandlerMap.get(destination);

                    // Explicit cast of the payload type
                    Class<?> payloadType = (Class<?>) payloadTypeClass;

                    // Use a raw type handler and cast it inside the subscribe method
                    subscribeRaw(destination, payloadType, handler);
                });
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                System.out.println("Transport Error: " + exception.getMessage());
                exception.printStackTrace();
                if (errorCallback != null) {
                    errorCallback.accept(exception);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static void subscribeRaw(String destination, Class<?> payloadType, Consumer<Object> handler) {
        if (stompSession != null && stompSession.isConnected()) {
            Subscription subscription = stompSession.subscribe(destination, new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return payloadType;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    handler.accept(payload);
                }
            });
            System.out.println("Subscribed to " + destination);
            activeSubscriptions.put(destination, subscription);
        } else {
            // Store subscription request to process after connection
            topicTypeMap.put(destination, payloadType);
            topicHandlerMap.put(destination, handler);
        }
    }

    public static void unsubscribe(String destination) {
        Subscription subscription = activeSubscriptions.get(destination);
        if (subscription != null) {
            subscription.unsubscribe();
            System.out.println("Unsubscribed from " + destination);
            activeSubscriptions.remove(destination);
        } else {
            System.out.println("No active subscription for " + destination);
        }
    }

    public static <T> void sendMessage(String destination, T message) throws SessionIsNotConnectedException {
        if (stompSession != null && stompSession.isConnected()) {
            stompSession.send(destination, message);
            System.out.println("Message sent to " + destination);
        } else {
            System.out.println("Stomp session is not connected.");
            throw new SessionIsNotConnectedException();
        }
    }

    public static void disconnect() {
        if (stompSession != null && stompSession.isConnected()) {
            stompSession.disconnect();
        }
        if (stompClient != null) {
            stompClient.stop();
        }
    }


}