package com.harun.liveSlide.network;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.network.ResponseStatus;
import com.harun.liveSlide.model.network.meeting.MeetingInitialInformationResponse;
import com.harun.liveSlide.model.network.participantList.DisconnectRequest;
import com.harun.liveSlide.model.network.participantList.DisconnectResponse;
import com.harun.liveSlide.model.network.participantList.SessionParticipantsRequest;
import com.harun.liveSlide.model.network.participantList.SessionParticipantsResponse;
import com.harun.liveSlide.model.network.pdfFile.UploadPDFResponse;
import com.harun.liveSlide.screens.mainScreen.MainScreen;
import javafx.application.Platform;

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
                "/topic/meetingInitialInformation/"
                        + GlobalVariables.SESSION_ID + "/" + GlobalVariables.USER_ID,
                MeetingInitialInformationResponse.class, response -> {
                    handleMeetingInitialInformationResponse(((MeetingInitialInformationResponse) response));
                });

        StompClient.subscribeRaw(
                "/topic/pageChanged/"
                        + GlobalVariables.SESSION_ID ,
                int.class, response -> {
                    handlePageChangedResponse(((int) response));
        });

        StompClient.subscribeRaw(
                "/topic/scrolledVertically/"
                        + GlobalVariables.SESSION_ID ,
                double.class, response -> {
                    handleScrolledVerticallyResponse(((double) response));
                });

        StompClient.subscribeRaw(
                "/topic/scrolledHorizontally/"
                        + GlobalVariables.SESSION_ID ,
                double.class, response -> {
                    handleScrolledHorizontallyResponse(((double) response));
                });

        StompClient.subscribeRaw(
                "/topic/zoomed/"
                        + GlobalVariables.SESSION_ID ,
                int.class, response -> {
                    handleZoomedResponse(((int) response));
                });

        if(GlobalVariables.userType != UserType.HOST_PRESENTER)
            getMeetingInitialInformation();
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

    public void loadPDF(String path , MeetingInitialInformationResponse response) {
        Platform.runLater(() -> {
            mainScreen.pdfViewer.loadPDF(path);
            if(response != null) {
                mainScreen.pdfViewer.goPage(response.getIndex());
                System.out.println("Meeting Zoom rate : " + response.getZoomRate());
                mainScreen.pdfViewer.zoomToZoomRate(response.getZoomRate());
                mainScreen.pdfViewer.scrollVerticallyTo(response.getvScrollValue());
                mainScreen.pdfViewer.scrollHorizontallyTo(response.gethScrollValue());
            }
        });
    }

    public void getMeetingInitialInformation() {
        StompClient.sendMessage("/app/getMeetingInitialInformation/" +
                GlobalVariables.SESSION_ID +
                "/" +
                GlobalVariables.USER_ID,""
                );
    }

    public void pageChanged(int index) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            StompClient.sendMessage("/app/pageChanged/" +
                    GlobalVariables.SESSION_ID ,index
            );
        }
    }

    public void scrolledVerticallyTo(double vValue) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            StompClient.sendMessage("/app/scrolledVertically/" +
                    GlobalVariables.SESSION_ID ,vValue
            );
        }
    }

    public void scrolledHorizontallyTo(double hValue) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            StompClient.sendMessage("/app/scrolledHorizontally/" +
                    GlobalVariables.SESSION_ID ,hValue
            );
        }
    }

    public void zoomed(int zoomRate) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            StompClient.sendMessage("/app/zoomed/" +
                    GlobalVariables.SESSION_ID ,zoomRate
            );
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

    private void handleMeetingInitialInformationResponse(MeetingInitialInformationResponse response) {
        System.out.println(response);
        if (response.getFileName() != null || !response.getFileName().equals("") && mainScreen.pdfViewer.pdfPages.isEmpty()) {
            File file = new File("src/meetingSlides/" + response.getFileName());
            if (!file.exists())
                s3Manager.downloadFile(response.getFileName(),"src/meetingSlides",response);
            else
                loadPDF(file.getAbsolutePath(),response);
        }
    }

    private void handlePageChangedResponse(int index) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER)
            if (!mainScreen.pdfViewer.pdfPages.isEmpty()){
                Platform.runLater(() -> {
                    mainScreen.pdfViewer.goPage(index);
                });
            }
    }

    private void handleScrolledHorizontallyResponse(double hValue) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                mainScreen.pdfViewer.scrollHorizontallyTo(hValue);
            });
        }
    }

    private void handleScrolledVerticallyResponse(double vValue) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                mainScreen.pdfViewer.scrollVerticallyTo(vValue);
            });
        }
    }

    private void handleZoomedResponse(int zoomRate) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                mainScreen.pdfViewer.zoomToZoomRate(zoomRate);
            });
        }
    }
}
