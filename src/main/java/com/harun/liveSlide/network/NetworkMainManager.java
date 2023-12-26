package com.harun.liveSlide.network;

import com.harun.liveSlide.components.pdfViewer.PDFPage;
import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.network.ResponseStatus;
import com.harun.liveSlide.model.network.meeting.PageChangedRequest;
import com.harun.liveSlide.model.network.meeting.PageChangedResponse;
import com.harun.liveSlide.model.network.participantList.DisconnectRequest;
import com.harun.liveSlide.model.network.participantList.DisconnectResponse;
import com.harun.liveSlide.model.network.participantList.SessionParticipantsRequest;
import com.harun.liveSlide.model.network.participantList.SessionParticipantsResponse;
import com.harun.liveSlide.model.network.pdfFile.DownloadPDFResponse;
import com.harun.liveSlide.model.network.pdfFile.UploadPDFResponse;
import com.harun.liveSlide.screens.mainScreen.MainScreen;
import javafx.application.Platform;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class NetworkMainManager {
    private final MainScreen mainScreen;
    private final S3Manager s3Manager;

    public NetworkMainManager(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        this.s3Manager = new S3Manager(this);

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
                "/topic/fileUploaded/"
                        + GlobalVariables.SESSION_ID,
                UploadPDFResponse.class, response -> {
                    handleUploadPdfResponse(((UploadPDFResponse) response));
        });

        StompClient.subscribeRaw(
                "/topic/pageChanged/"
                        + GlobalVariables.SESSION_ID ,
                PageChangedResponse.class, response -> {
                    handlePageChangedResponse(((PageChangedResponse) response));
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

    public void uploadPDFToS3(String path) {
        if(GlobalVariables.userType == UserType.HOST_PRESENTER) {
            File file = new File(path);
            s3Manager.uploadFile(file.getName(),path);
        }
    }

    public void loadPDFWithNotify(String fileName , String path) {
        Platform.runLater(() -> {
            mainScreen.pdfViewer.loadPDF(path);
            StompClient.sendMessage("/app/fileUploaded/" + GlobalVariables.SESSION_ID,fileName);
        });
    }

    public void loadPDF(String path) {
        Platform.runLater(() -> {
            mainScreen.pdfViewer.loadPDF(path);
        });
    }

    public void pageChanged(int index , PDFPage pdfPage) {
    }


    private void handleGetParticipantResponse(SessionParticipantsResponse response) {
        Platform.runLater(() -> {
            mainScreen.participantTab.setParticipants(response.getParticipants());
        });
    }


    private void handleDisconnectResponse(DisconnectResponse response) {
        Platform.runLater(() -> {
            System.out.println(response.status);
            if (response.status == ResponseStatus.SUCCESS) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    private void handleUploadPdfResponse(UploadPDFResponse response) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER &&
        GlobalVariables.userType != UserType.HOST_SPECTATOR){
            s3Manager.downloadFile(response.fileName, "src/meetingSlides");
        }
    }

    private void handlePageChangedResponse(PageChangedResponse response) {
        System.out.println("Sayfa Değişti");
    }
}
