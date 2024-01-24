package com.harun.liveSlide.network;

import com.harun.liveSlide.components.pdfViewer.PDFTool;
import com.harun.liveSlide.components.pdfViewer.PenColor;
import com.harun.liveSlide.components.pdfViewer.PenEraserSize;
import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.exceptions.SessionIsNotConnectedException;
import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.MouseCoordinate;
import com.harun.liveSlide.model.Participant;
import com.harun.liveSlide.model.network.ResponseStatus;
import com.harun.liveSlide.model.network.meeting.*;
import com.harun.liveSlide.model.network.participantList.*;
import com.harun.liveSlide.screens.mainScreen.MainScreen;
import javafx.application.Platform;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.Date;

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
                "/topic/participantJoined/"
                        + GlobalVariables.SESSION_ID,
                ParticipantJoinedEvent.class, response -> {
                    handleParticipantJoinedResponse(((ParticipantJoinedEvent) response));
                });

        StompClient.subscribeRaw(
                "/topic/participantDisconnected/"
                        + GlobalVariables.SESSION_ID,
                ParticipantDisconnectedEvent.class, response -> {
                    handleParticipantDisconnectedResponse(((ParticipantDisconnectedEvent) response));
                });

        StompClient.subscribeRaw(
                "/topic/fileUploaded/"
                        + GlobalVariables.SESSION_ID,
                FileUploadedEvent.class, response -> {
                    handleUploadPdfResponse(((FileUploadedEvent) response));
        });

        StompClient.subscribeRaw(
                "/topic/meetingSynchInformation/"
                        + GlobalVariables.SESSION_ID + "/" + GlobalVariables.USER_ID,
                MeetingSynchInformationResponse.class, response -> {
                    handleMeetingSynchInformationResponse(((MeetingSynchInformationResponse) response));
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
                PageChangedEvent.class, response -> {
                    handlePageChangedResponse(((PageChangedEvent) response));
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

        StompClient.subscribeRaw(
                "/topic/rotated/"
                        + GlobalVariables.SESSION_ID ,
                int.class, response -> {
                    handleRotatedResponse(((int) response));
                });

        StompClient.subscribeRaw(
                "/topic/activeToolChanged/"
                        + GlobalVariables.SESSION_ID ,
                PDFTool.class, response -> {
                    handleActiveToolChangedResponse(((PDFTool) response));
                });

        StompClient.subscribeRaw(
                "/topic/penSizeChanged/"
                        + GlobalVariables.SESSION_ID ,
                PenEraserSize.class, response -> {
                    handlePenSizeChangedResponse(((PenEraserSize) response));
                });

        StompClient.subscribeRaw(
                "/topic/penColorChanged/"
                        + GlobalVariables.SESSION_ID ,
                PenColor.class, response -> {
                    handlePenColorChangedResponse(((PenColor) response));
                });

        StompClient.subscribeRaw(
                "/topic/eraserSizeChanged/"
                        + GlobalVariables.SESSION_ID ,
                PenEraserSize.class, response -> {
                    handleEraserSizeChangedResponse(((PenEraserSize) response));
                });

        StompClient.subscribeRaw(
                "/topic/pointed/"
                        + GlobalVariables.SESSION_ID ,
                PointedEvent.class, response -> {
                    handlePointedResponse(((PointedEvent) response));
                });

        StompClient.subscribeRaw(
                "/topic/canvasPressed/"
                        + GlobalVariables.SESSION_ID ,
                CanvasEvent.class, response -> {
                    handleCanvasPressedResponse(((CanvasEvent) response));
                });

        StompClient.subscribeRaw(
                "/topic/canvasDragged/"
                        + GlobalVariables.SESSION_ID ,
                CanvasEvent.class, response -> {
                    handleCanvasDraggedResponse(((CanvasEvent) response));
                });

        StompClient.subscribeRaw(
                "/topic/requestControl/"
                        + GlobalVariables.SESSION_ID ,
                RequestControlEvent.class, response -> {
                    handleRequestControlResponse(((RequestControlEvent) response));
                });

        StompClient.subscribeRaw(
                "/topic/presenterChanged/"
                        + GlobalVariables.SESSION_ID ,
                PresenterChangedEvent.class, response -> {
                    handlePresenterChangedResponse(((PresenterChangedEvent) response));
                });

        if(GlobalVariables.userType != UserType.HOST_PRESENTER)
            getMeetingInitialInformation();
    }

    public void getParticipants() {
        SessionParticipantsRequest req = new SessionParticipantsRequest(GlobalVariables.SESSION_ID,GlobalVariables.USER_ID);
        try {
            StompClient.sendMessage("/app/getParticipants",req);
        } catch (SessionIsNotConnectedException e) {
            mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
        }
    }

    public void disconnect() {
        DisconnectRequest req = new DisconnectRequest(GlobalVariables.SESSION_ID,GlobalVariables.USER_ID);
        try {
            StompClient.sendMessage("/app/disconnect",req);
        } catch (SessionIsNotConnectedException e) {
            mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
        }
    }

    public void uploadPDFToS3(String path,double hostScreenWidth) {
        if(GlobalVariables.userType == UserType.HOST_PRESENTER) {
            File file = new File(path);
            mainScreen.topSide.setStatusLabelText("Slide is uploading to server...");
            s3Manager.uploadFile(file.getName(),path,hostScreenWidth);
        }
    }

    public void loadPDFWithNotify(String fileName , String path, double hostScreenWidth) {
        Platform.runLater(() -> {
            mainScreen.pdfViewer.loadPDF(path);
            try {
                StompClient.sendMessage("/app/fileUploaded/" + GlobalVariables.SESSION_ID,
                        new FileUploadedEvent(
                                fileName,
                                mainScreen.pdfViewer.getPdfViewerNavigationController().getPageCount(),
                                hostScreenWidth));
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
            mainScreen.topSide.setStatusLabelText("You are presenting");
        });
    }

    public void loadPDF(String path , MeetingInitialInformationResponse initialResponse, FileUploadedEvent fileUploadedEvent) {
        Platform.runLater(() -> {
            double hostScreenWidth = initialResponse == null ? fileUploadedEvent.getHostScreenWidth() : initialResponse.getHostScreenWidth();

            mainScreen.pdfViewer.loadPDF(path,hostScreenWidth);
            if (initialResponse != null) {
                mainScreen.topSide.setStatusLabelText("Loading drawings...");
                mainScreen.pdfViewer.drawPages(initialResponse.getCanvasEvents());
            }
            mainScreen.topSide.setStatusLabelText("Synchronizing...");
            getMeetingSynchInformation();
        });
    }

    public void getMeetingSynchInformation() {
        try {
            StompClient.sendMessage("/app/getMeetingSynchInformation/" +
                    GlobalVariables.SESSION_ID +
                    "/" +
                    GlobalVariables.USER_ID,""
                    );
        } catch (SessionIsNotConnectedException e) {
            mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
        }
    }

    private void getMeetingInitialInformation() {
        try {
            StompClient.sendMessage("/app/getMeetingInitialInformation/" +
                    GlobalVariables.SESSION_ID +
                    "/" +
                    GlobalVariables.USER_ID,""
            );
        } catch (SessionIsNotConnectedException e) {
            mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
        }
    }

    public void pageChanged(int index,int zoomRate,double vValue,double hValue) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/pageChanged/" +
                        GlobalVariables.SESSION_ID , new PageChangedEvent(index , zoomRate ,vValue, hValue)
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void scrolledVerticallyTo(double vValue) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/scrolledVertically/" +
                        GlobalVariables.SESSION_ID ,vValue
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void scrolledHorizontallyTo(double hValue) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/scrolledHorizontally/" +
                        GlobalVariables.SESSION_ID ,hValue
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void zoomed(int zoomRate) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/zoomed/" +
                        GlobalVariables.SESSION_ID ,zoomRate
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void rotated(int rotateRate) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/rotated/" +
                        GlobalVariables.SESSION_ID ,rotateRate
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void activeToolChanged(PDFTool pdfTool) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/activeToolChanged/" +
                        GlobalVariables.SESSION_ID ,pdfTool
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void penSizeChanged(PenEraserSize size) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/penSizeChanged/" +
                        GlobalVariables.SESSION_ID ,size
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void penColorChanged(PenColor penColor) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/penColorChanged/" +
                        GlobalVariables.SESSION_ID ,penColor
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void eraserSizeChanged(PenEraserSize size) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/eraserSizeChanged/" +
                        GlobalVariables.SESSION_ID ,size
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void pointed(double x, double y) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/pointed/" +
                        GlobalVariables.SESSION_ID , new PointedEvent(x,y)
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void canvasPressed(MouseCoordinate mouseCoordinate) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/canvasPressed/" +
                        GlobalVariables.SESSION_ID , new CanvasEvent(mouseCoordinate.x, mouseCoordinate.y)
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void canvasDragged(MouseCoordinate mouseCoordinate) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
            try {
                StompClient.sendMessage("/app/canvasDragged/" +
                        GlobalVariables.SESSION_ID , new CanvasEvent(mouseCoordinate.x, mouseCoordinate.y)
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void requestControl(boolean isRequestControl) {
        if (GlobalVariables.userType == UserType.PARTICIPANT_SPECTATOR){
            try {
                StompClient.sendMessage("/app/requestControl/" +
                        GlobalVariables.SESSION_ID + "/" + GlobalVariables.USER_ID , isRequestControl
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    public void changePresenter(String userID) {
        if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.HOST_SPECTATOR){
            try {
                StompClient.sendMessage("/app/presenterChanged/" +
                        GlobalVariables.SESSION_ID , userID
                );
            } catch (SessionIsNotConnectedException e) {
                mainScreen.topSide.setStatusLabelText("Server connection is lost. Please relaunch !");
            }
        }
    }

    private void handleGetParticipantResponse(SessionParticipantsResponse response) {
        Platform.runLater(() -> {
            mainScreen.participantTab.setParticipants(response.getParticipants());
        });
    }

    private void handleParticipantJoinedResponse(ParticipantJoinedEvent response) {
        Platform.runLater(() -> {
            mainScreen.participantTab.addParticipant(
                    new Participant(response.getUserID(), response.getUserName(), UserType.PARTICIPANT_SPECTATOR,false)
            );
        });
    }

    private void handleParticipantDisconnectedResponse(ParticipantDisconnectedEvent response) {
        Platform.runLater(() -> {
            mainScreen.participantTab.removeParticipant(response.getUserID());
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

    private void handleUploadPdfResponse(FileUploadedEvent response) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER &&
        GlobalVariables.userType != UserType.HOST_SPECTATOR){
            Platform.runLater(() -> {
                mainScreen.topSide.setStatusLabelText("Slide is downloading from server...");
                if (mainScreen.pdfViewer.pdfPages != null)
                    mainScreen.pdfViewer.pdfPages.clear();
                mainScreen.pdfViewer.viewArea.setContent(null);
            });

            // Define the directory where files will be downloaded
            String userHomeDir = System.getProperty("user.home");
            String downloadDir = userHomeDir + File.separator + "meetingSlides";

            // Create the directory if it doesn't exist
            new File(downloadDir).mkdirs();

            s3Manager.downloadFile(response.getFileName(), downloadDir , null, response);
        }
    }

    private void handleMeetingSynchInformationResponse(MeetingSynchInformationResponse response) {
        Platform.runLater(() -> {
            mainScreen.pdfViewer.goPage(response.getIndex());
            mainScreen.pdfViewer.rotateToRotateRate(response.getRotateRate());
            mainScreen.pdfViewer.zoomToZoomRate(response.getZoomRate());
            mainScreen.pdfViewer.scrollVerticallyTo(response.getvScrollValue());
            mainScreen.pdfViewer.scrollHorizontallyTo(response.gethScrollValue());
            mainScreen.pdfViewer.changeActiveTool(response.getActiveTool());
            mainScreen.pdfViewer.changeActiveEraserSize(response.getEraserSize());
            mainScreen.pdfViewer.changeActivePenSize(response.getPenSize());
            mainScreen.pdfViewer.changeActivePenColor(response.getPenColor());
            mainScreen.topSide.setStatusLabelText(response.getPresenterName() + " is presenting");
        });
    }

    private void handleMeetingInitialInformationResponse(MeetingInitialInformationResponse initialResponse) {
        //System.out.println(initialResponse);
        if (initialResponse.getFileName() != null || mainScreen.pdfViewer.pdfPages.isEmpty()) {
            // Define the directory where files will be downloaded
            String userHomeDir = System.getProperty("user.home");
            String downloadDir = userHomeDir + File.separator + "meetingSlides";

            // Create the directory if it doesn't exist
            new File(downloadDir).mkdirs();

            File file = new File(downloadDir + File.separator + initialResponse.getFileName());
            if (!file.exists())
                s3Manager.downloadFile(initialResponse.getFileName(), downloadDir , initialResponse , null);
            else
                loadPDF(file.getAbsolutePath(),initialResponse,null);
        }
    }

    private void handlePageChangedResponse(PageChangedEvent event) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER)
            if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty()){
                Platform.runLater(() -> {
                    mainScreen.pdfViewer.goPage(event.getIndex());
                    mainScreen.pdfViewer.zoomToZoomRate(event.getZoomRate());
                    mainScreen.pdfViewer.scrollVerticallyTo(event.getvValue());
                    mainScreen.pdfViewer.scrollHorizontallyTo(event.gethValue());
                });
            }
    }

    private void handleScrolledHorizontallyResponse(double hValue) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty())
                    mainScreen.pdfViewer.scrollHorizontallyTo(hValue);
            });
        }
    }

    private void handleScrolledVerticallyResponse(double vValue) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty())
                    mainScreen.pdfViewer.scrollVerticallyTo(vValue);
            });
        }
    }

    private void handleZoomedResponse(int zoomRate) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty())
                    mainScreen.pdfViewer.zoomToZoomRate(zoomRate);
            });
        }
    }

    private void handleRotatedResponse(int rotateRate) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty())
                    mainScreen.pdfViewer.rotateToRotateRate(rotateRate);
            });
        }
    }

    private void handleActiveToolChangedResponse(PDFTool activeTool) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty())
                    mainScreen.pdfViewer.changeActiveTool(activeTool);
            });
        }
    }

    private void handlePenSizeChangedResponse(PenEraserSize size) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty())
                    mainScreen.pdfViewer.changeActivePenSize(size);
            });
        }
    }

    private void handlePenColorChangedResponse(PenColor color) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty())
                    mainScreen.pdfViewer.changeActivePenColor(color);
            });
        }
    }

    private void handleEraserSizeChangedResponse(PenEraserSize size) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty())
                    mainScreen.pdfViewer.changeActiveEraserSize(size);
            });
        }
    }

    private void handlePointedResponse(PointedEvent response) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty())
                    mainScreen.pdfViewer.point(response.getX(),response.getY());
            });
        }
    }

    private void handleCanvasPressedResponse(CanvasEvent response) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty())
                    mainScreen.pdfViewer.canvasPressed(new MouseCoordinate(response.getX(), response.getY()));
            });
        }
    }

    private void handleCanvasDraggedResponse(CanvasEvent response) {
        if (GlobalVariables.userType != UserType.HOST_PRESENTER && GlobalVariables.userType != UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                if (mainScreen.pdfViewer.pdfPages != null && !mainScreen.pdfViewer.pdfPages.isEmpty())
                    mainScreen.pdfViewer.canvasDragged(new MouseCoordinate(response.getX(), response.getY()));
            });
        }
    }

    private void handleRequestControlResponse(RequestControlEvent event) {
        Platform.runLater(() -> {
            mainScreen.participantTab.changeParticipantRequestStatus(event.getUserID(),event.isRequestControl());
        });
    }

    private void handlePresenterChangedResponse(PresenterChangedEvent event) {
        String userID = event.getUserID();
        if (GlobalVariables.USER_ID.equals(userID) && GlobalVariables.userType == UserType.PARTICIPANT_SPECTATOR) {
            GlobalVariables.userType = UserType.PARTICIPANT_PRESENTER;
            Platform.runLater(() -> {
                mainScreen.topSide.getController().changeRequestControlStatus(false);
                mainScreen.topSide.setStatusLabelText("You are presenting");
                mainScreen.authLayoutController.updateLayoutAccordingToUserType();
            });
        }
        else if(!GlobalVariables.USER_ID.equals(userID) && GlobalVariables.userType == UserType.PARTICIPANT_SPECTATOR) {
            Platform.runLater(() -> {
                mainScreen.topSide.setStatusLabelText(event.getUserName() + " is presenting");
            });
        }
        else if(GlobalVariables.USER_ID.equals(userID) && GlobalVariables.userType == UserType.HOST_SPECTATOR) {
            GlobalVariables.userType = UserType.HOST_PRESENTER;
            Platform.runLater(() -> {
                mainScreen.topSide.setStatusLabelText("You are presenting");
                mainScreen.authLayoutController.updateLayoutAccordingToUserType();
            });
        }
        else if(!GlobalVariables.USER_ID.equals(userID) && GlobalVariables.userType == UserType.HOST_SPECTATOR) {
            Platform.runLater(() -> {
                mainScreen.topSide.setStatusLabelText(event.getUserName() + " is presenting");
            });
        }
        else if(!GlobalVariables.USER_ID.equals(userID) && GlobalVariables.userType == UserType.HOST_PRESENTER) {
            GlobalVariables.userType = UserType.HOST_SPECTATOR;
            Platform.runLater(() -> {
                mainScreen.topSide.setStatusLabelText(event.getUserName() + " is presenting");
                mainScreen.authLayoutController.updateLayoutAccordingToUserType();
            });
        }
        else if(GlobalVariables.USER_ID.equals(userID) && GlobalVariables.userType == UserType.HOST_PRESENTER) {
            Platform.runLater(() -> {
                mainScreen.topSide.setStatusLabelText("You are presenting");
            });
        }
        else if(GlobalVariables.USER_ID.equals(userID) && GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER) {
            Platform.runLater(() -> {
                mainScreen.topSide.setStatusLabelText("You are presenting");
            });
        }
        else if(!GlobalVariables.USER_ID.equals(userID) && GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER) {
            GlobalVariables.userType = UserType.PARTICIPANT_SPECTATOR;
            Platform.runLater(() -> {
                mainScreen.topSide.setStatusLabelText(event.getUserName() + " is presenting");
                mainScreen.authLayoutController.updateLayoutAccordingToUserType();
            });
        }

    }
}
