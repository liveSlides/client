package com.harun.liveSlide.network;

import com.harun.liveSlide.model.network.*;
import com.harun.liveSlide.screens.loginScreen.LoginScreen;

import java.util.UUID;

public class NetworkLoginManager{
    private final LoginScreen loginScreen;

    public NetworkLoginManager(LoginScreen loginScreen) {
        this.loginScreen = loginScreen;

        StompClient.initialize();
        StompClient.connect();

        StompClient.subscribeRaw("/topic/sessionStatus", SessionInitialResponse.class, status -> {
            handleResponse(((SessionInitialResponse) status));
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

    public void unsubscribeFromLogin() {
        StompClient.unsubscribe("/topic/sessionStatus");
    }

    public void handleResponse(SessionInitialResponse sessionInitialResponse) {
        System.out.println(sessionInitialResponse.getType());
        System.out.println(sessionInitialResponse.getSessionID());
        System.out.println(sessionInitialResponse.getStatus());

        if (    sessionInitialResponse.getType() == SessionInitializeType.HOST &&
                sessionInitialResponse.getStatus() == ResponseStatus.SUCCESS
        ) {
            loginScreen.showMainScreen(sessionInitialResponse.getSessionID(),sessionInitialResponse.getType());
        }
        else if (sessionInitialResponse.getType() == SessionInitializeType.HOST &&
                sessionInitialResponse.getStatus() == ResponseStatus.ERROR) {
            loginScreen.showHostError("An error has occured while trying to host!");
        }
        else if (sessionInitialResponse.getType() == SessionInitializeType.JOIN &&
                sessionInitialResponse.getStatus() == ResponseStatus.SUCCESS) {
            loginScreen.showMainScreen(sessionInitialResponse.getSessionID(),sessionInitialResponse.getType());
        }
        else if (sessionInitialResponse.getType() == SessionInitializeType.JOIN &&
                sessionInitialResponse.getStatus() == ResponseStatus.ERROR) {
            loginScreen.showHostError("An error has occured while trying to join!");
        }
        else {
            loginScreen.showHostError("An error has occured!");
        }
    }
}
