package com.harun.liveSlide.network;

import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.network.ResponseStatus;
import com.harun.liveSlide.model.network.participantList.DisconnectRequest;
import com.harun.liveSlide.model.network.participantList.DisconnectResponse;
import com.harun.liveSlide.model.network.participantList.SessionParticipantsRequest;
import com.harun.liveSlide.model.network.participantList.SessionParticipantsResponse;
import com.harun.liveSlide.model.network.pdfFile.UploadPDFRequest;
import com.harun.liveSlide.model.network.pdfFile.UploadPDFResponse;
import com.harun.liveSlide.screens.mainScreen.MainScreen;
import javafx.application.Platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;

public class NetworkMainManager {
    private final MainScreen mainScreen;
    private final SlideUploader slideUploader;

    public NetworkMainManager(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        this.slideUploader = new SlideUploader();

        StompClient.subscribeRaw(
                "/topic/getParticipants/"
                        + GlobalVariables.SESSION_ID
                        + "/" + GlobalVariables.USER_ID,
                SessionParticipantsResponse.class, response -> {
                    handleGetParticipantResponse(((SessionParticipantsResponse) response));
        });

        StompClient.subscribeRaw(
                "/topic/disconnect/"
                        + GlobalVariables.SESSION_ID
                        + "/" + GlobalVariables.USER_ID,
                DisconnectResponse.class, response -> {
                    handleDisconnectResponse(((DisconnectResponse) response));
        });

        StompClient.subscribeRaw(
                "/topic/uploadPDF/"
                        + GlobalVariables.SESSION_ID,
                UploadPDFResponse.class, response -> {
                    handleUploadPdfResponse(((UploadPDFResponse) response));
                });
    }

    public void getParticipants() {
        SessionParticipantsRequest req = new SessionParticipantsRequest(GlobalVariables.SESSION_ID,GlobalVariables.USER_ID);
        StompClient.sendMessage("/app/getParticipants",req);
    }

    public void disconnect() {
        DisconnectRequest req = new DisconnectRequest(GlobalVariables.SESSION_ID,GlobalVariables.USER_ID);
        StompClient.sendMessage("/app/disconnect",req);
    }

    public void uploadPDF(String path) {
        slideUploader.setFilePath(path);
        slideUploader.startToUploadSlide();
    }


    private void handleGetParticipantResponse(SessionParticipantsResponse response) {
        Platform.runLater(() -> {
            mainScreen.participantTab.setParticipants(response.getParticipants());
        });
    }


    private void handleDisconnectResponse(DisconnectResponse response) {
        Platform.runLater(() -> {
            if (response.status == ResponseStatus.SUCCESS) {
                Platform.exit();
            }
        });
    }

    private void handleUploadPdfResponse(UploadPDFResponse response) {
        System.out.println("PDF Servera yüklendi");
    }
}
