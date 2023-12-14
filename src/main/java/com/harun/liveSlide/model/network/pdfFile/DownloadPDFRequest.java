package com.harun.liveSlide.model.network.pdfFile;

public class DownloadPDFRequest {
    public String sessionID;
    public String userID;
    public long gatheredSize;

    public DownloadPDFRequest() {

    }

    public DownloadPDFRequest(String sessionID, String userID, long gatheredSize) {
        this.sessionID = sessionID;
        this.userID = userID;
        this.gatheredSize = gatheredSize;
    }


    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getGatheredSize() {
        return gatheredSize;
    }

    public void setGatheredSize(long gatheredSize) {
        this.gatheredSize = gatheredSize;
    }
}
