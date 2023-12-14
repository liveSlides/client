package com.harun.liveSlide.model.network.pdfFile;

public class UploadPDFRequest {
    public String encodedFileContent;
    public String sessionID;
    public String userID;

    public UploadPDFRequest() {

    }

    public UploadPDFRequest(String encodedFileContent, String sessionID, String userID) {
        this.encodedFileContent = encodedFileContent;
        this.sessionID = sessionID;
        this.userID = userID;
    }

    public String getFileContent() {
        return encodedFileContent;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setFileContent(String encodedFileContent) {
        this.encodedFileContent = encodedFileContent;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
