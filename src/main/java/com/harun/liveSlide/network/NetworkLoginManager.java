package com.harun.liveSlide.network;

import com.harun.liveSlide.model.network.SessionCreationRequest;
import com.harun.liveSlide.model.network.SessionInitialResponse;
import com.harun.liveSlide.model.network.SessionJoinRequest;
import com.harun.liveSlide.screens.loginScreen.LoginScreen;

import java.util.UUID;

public class NetworkLoginManager{
    private final LoginScreen loginScreen;

    public NetworkLoginManager(LoginScreen loginScreen) {
        this.loginScreen = loginScreen;

        StompClient.initialize();
        StompClient.connect();

        StompClient.subscribeRaw("/topic/sessionStatus", SessionInitialResponse.class, status -> {
            System.out.println("Received status: " + ((SessionInitialResponse) status).getStatus());
            System.out.println("Received session ID: " + ((SessionInitialResponse) status).getSessionID());
            System.out.println("Received init type: " + ((SessionInitialResponse) status).getType());
        });
    }

    public void host(String hostName) {
        UUID id = UUID.randomUUID();
        SessionCreationRequest info = new SessionCreationRequest(id.toString(),hostName);
        StompClient.sendMessage("/app/startSession",info);
    }

    public void join(String sessionID , String participantName) {
        SessionJoinRequest info = new SessionJoinRequest(sessionID,participantName);
        StompClient.sendMessage("/app/joinSession",info);
    }
}
