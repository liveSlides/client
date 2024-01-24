package com.harun.liveSlide.network;

import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.network.*;
import com.harun.liveSlide.model.network.login.SessionCreationRequest;
import com.harun.liveSlide.model.network.login.SessionInitialResponse;
import com.harun.liveSlide.model.network.login.SessionInitializeType;
import com.harun.liveSlide.model.network.login.SessionJoinRequest;
import com.harun.liveSlide.screens.loginScreen.LoginScreen;
import javafx.application.Platform;

import java.util.UUID;

public class NetworkLoginManager{
    private final LoginScreen loginScreen;

    public NetworkLoginManager(LoginScreen loginScreen) {
        this.loginScreen = loginScreen;

        StompClient.initialize();
        try {
            StompClient.connect(e -> {
                loginScreen.setErrorLabelText("Couldn't connect to server. Please relaunch again.");
            });
        } catch (Exception e) {
            loginScreen.setErrorLabelText("Couldn't connect to server. Please relaunch again.");
        }


        StompClient.subscribeRaw("/topic/sessionStatus", SessionInitialResponse.class, status -> {
            handleResponse(((SessionInitialResponse) status));
        });
    }

    public void host(String hostName) {
        UUID id = UUID.randomUUID();
        SessionCreationRequest req = new SessionCreationRequest(GlobalVariables.USER_ID,id.toString(),hostName);
        StompClient.sendMessage("/app/startSession",req);
    }

    public void join(String sessionID , String participantName) {
        SessionJoinRequest req = new SessionJoinRequest(GlobalVariables.USER_ID,sessionID,participantName);
        StompClient.sendMessage("/app/joinSession",req);
    }

    public void unsubscribeFromLogin() {
        StompClient.unsubscribe("/topic/sessionStatus");
    }

    private void handleResponse(SessionInitialResponse sessionInitialResponse) {
        System.out.println(sessionInitialResponse);
        Platform.runLater(() -> {
            if (    sessionInitialResponse.getType() == SessionInitializeType.HOST &&
                    sessionInitialResponse.getStatus() == ResponseStatus.SUCCESS
            ) {
                loginScreen.showMainScreen(sessionInitialResponse.getSessionID(), sessionInitialResponse.getCreationTime() ,sessionInitialResponse.getType());
                loginScreen.showSessionIDInformationScreen(sessionInitialResponse.getSessionID());
            }
            else if (sessionInitialResponse.getType() == SessionInitializeType.HOST &&
                    sessionInitialResponse.getStatus() == ResponseStatus.ERROR) {
                loginScreen.showHostError("An error has occurred while trying to host!");
            }
            else if (sessionInitialResponse.getType() == SessionInitializeType.JOIN &&
                    sessionInitialResponse.getStatus() == ResponseStatus.SUCCESS) {
                loginScreen.showMainScreen(sessionInitialResponse.getSessionID(), sessionInitialResponse.getCreationTime() ,sessionInitialResponse.getType());
            }
            else if (sessionInitialResponse.getType() == SessionInitializeType.JOIN &&
                    sessionInitialResponse.getStatus() == ResponseStatus.ERROR) {
                loginScreen.showJoinError("An error has occurred while trying to join!");
            }
            else {
                loginScreen.showHostError("An error has occurred!");
            }
        });
    }
}
