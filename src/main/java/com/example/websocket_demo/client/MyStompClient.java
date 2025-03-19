package com.example.websocket_demo.client;

import com.example.websocket_demo.Message;
import com.example.websocket_demo.WebsocketConfig;
import com.example.websocket_demo.WebsocketController;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyStompClient {
    private static StompSession session;
    private String username;

    public MyStompClient(String username) throws ExecutionException,InterruptedException {
        this.username = username;

        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler(username);
        String url = "ws://localhost:8080/ws";  //use ws:// for webSocket

        session = stompClient.connectAsync(url, sessionHandler).get();
    }
    public static void sendMessage(Message message){
        try{
            session.send("/app/message",message);
            System.out.println("Message Sent: "+message.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
