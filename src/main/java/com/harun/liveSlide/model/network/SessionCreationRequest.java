package com.harun.liveSlide.model.network;

public class SessionCreationRequest {
    private String sessionID;
    private String hostName;

    public SessionCreationRequest() {

    }

    public SessionCreationRequest(String sessionID, String hostName) {
        this.sessionID = sessionID;
        this.hostName = hostName;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getHostName() {
        return hostName;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
