package com.harun.liveSlide.model.network.login;

import com.harun.liveSlide.model.network.ResponseStatus;

public class SessionInitialResponse {
    private String sessionID;
    private ResponseStatus status;
    private SessionInitializeType type;
    private String creationTime;

    public SessionInitialResponse() {
    }

    public SessionInitialResponse(String sessionID, ResponseStatus status, SessionInitializeType type , String creationTime) {
        this.sessionID = sessionID;
        this.status = status;
        this.type = type;
        this.creationTime = creationTime;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public SessionInitializeType getType() {
        return type;
    }

    public void setType(SessionInitializeType type) {
        this.type = type;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "SessionInitialResponse{" +
                "sessionID='" + sessionID + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", creationTime='" + creationTime + '\'' +
                '}';
    }
}

