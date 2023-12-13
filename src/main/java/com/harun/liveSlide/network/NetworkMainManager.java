package com.harun.liveSlide.network;

import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.network.SessionInitialResponse;
import com.harun.liveSlide.model.network.SessionParticipantsRequest;
import com.harun.liveSlide.model.network.SessionParticipantsResponse;
import com.harun.liveSlide.screens.loginScreen.LoginScreen;
import com.harun.liveSlide.screens.mainScreen.MainScreen;

public class NetworkMainManager {
    private final MainScreen mainScreen;

    public NetworkMainManager(MainScreen mainScreen) {
        this.mainScreen = mainScreen;

        StompClient.subscribeRaw(
                "/topic/getParticipants",
                SessionParticipantsResponse.class, response -> {
            System.out.println("Katılımcılar: " + ((SessionParticipantsResponse) response).getParticipants() );
        });
    }

    public void getParticipants() {
        SessionParticipantsRequest req = new SessionParticipantsRequest(GlobalVariables.SESSION_ID,GlobalVariables.USER_ID);
        StompClient.sendMessage("/app/getParticipants",req);
    }
}
