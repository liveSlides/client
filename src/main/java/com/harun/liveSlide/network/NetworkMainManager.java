package com.harun.liveSlide.network;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.network.ResponseStatus;
import com.harun.liveSlide.model.network.participantList.DisconnectRequest;
import com.harun.liveSlide.model.network.participantList.DisconnectResponse;
import com.harun.liveSlide.model.network.participantList.SessionParticipantsRequest;
import com.harun.liveSlide.model.network.participantList.SessionParticipantsResponse;
import com.harun.liveSlide.model.network.pdfFile.DownloadPDFResponse;
import com.harun.liveSlide.model.network.pdfFile.UploadPDFResponse;
import com.harun.liveSlide.screens.mainScreen.MainScreen;
import javafx.application.Platform;

import java.io.File;

public class NetworkMainManager {
    private final MainScreen mainScreen;
    private final SlideUploader slideUploader;
    private final SlideDownloader slideDownloader;

    public NetworkMainManager(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        this.slideUploader = new SlideUploader();
        this.slideDownloader = new SlideDownloader();

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

        StompClient.subscribeRaw(
                "/topic/downloadPDF/"
                        + GlobalVariables.SESSION_ID + "/" + GlobalVariables.USER_ID,
                DownloadPDFResponse.class, response -> {
                    handleDownloadPdfResponse(((DownloadPDFResponse) response));
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
        if(GlobalVariables.userType == UserType.HOST_PRESENTER) {
            slideUploader.setFilePath(path);
            slideUploader.startToUploadSlide();
        }
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
        if (GlobalVariables.userType == UserType.PARTICIPANT_SPECTATOR){
            System.out.println("PDF Servera yüklendi");
            slideDownloader.downloadSlide();
        }
    }

    private void handleDownloadPdfResponse(DownloadPDFResponse response) {
        System.out.println("PDF tamamı yüklendi");

        slideDownloader.pdfFile = null;
        String folderPath = "src/meetingSlides";
        String filePath = folderPath + File.separator + response.getFileName();

        Platform.runLater(() -> {
            mainScreen.pdfViewer.loadPDF(filePath);
        });

    }
}