package com.example.websocket_demo.client;

import com.example.websocket_demo.Message;

import java.util.ArrayList;

public interface MessageListener {

    abstract void onMessageRecieve(Message message);
    abstract void onActiveUsersUpdated(ArrayList<String> users);
}
