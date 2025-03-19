package com.example.websocket_demo.client;

import com.example.websocket_demo.Message;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private  String username;

    public MyStompSessionHandler(String username){
        this.username =username;
    }

    @Override
    public void afterConnected (StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/message" ,new StompFrameHandler(){
            @Override
            public Type getPayloadType(StompHeaders headers){
                return Message.class;
            }

            @Override
            public void handleFrame(StompHeaders headers,Object payload){
                try{
                    if (payload instanceof Message){
                        Message message = (Message) payload;
                        System.out.println("Received Message:"+message.getUser()+":"+message.getMessage());
                    }else{
                        System.out.println("Received Unexpected payload type: " + payload.getClass());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void handleTransportError(StompSession session,Throwable exception){
        exception.printStackTrace();
    }

}
