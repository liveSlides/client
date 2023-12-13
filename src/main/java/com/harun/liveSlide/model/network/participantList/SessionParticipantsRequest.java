package com.harun.liveSlide.model.network.participantList;

public class SessionParticipantsRequest {
    private String sessionId;
    private String userID;

    public SessionParticipantsRequest() {

    }

    public SessionParticipantsRequest(String sessionId,String userID) {
        this.sessionId = sessionId;
        this.userID = userID;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
