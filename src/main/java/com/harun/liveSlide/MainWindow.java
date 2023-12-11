package com.harun.liveSlide;

import com.harun.liveSlide.components.meetingSideBar.MeetingParticipantsBar;
import com.harun.liveSlide.components.meetingTopBar.MeetingTopBar;
import com.harun.liveSlide.components.pdfViewer.PDFViewer;
import com.harun.liveSlide.components.pdfViewer.PDFViewerObserver;
import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.network.NetworkManager;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Pane {

    public MeetingTopBar topSide;
    private MeetingParticipantsBar participantTab;

    public PDFViewer pdfViewer;
    public PDFViewerObserver pdfViewerObserver;
    public NetworkManager networkManager;
    public AuthLayoutController authLayoutController;
    public double sceneHeight;

    public MainWindow(Stage stage , double sceneWidth , double sceneHeight) throws IOException {
        this.getStyleClass().add("main-window");

        BorderPane mainGrid = new BorderPane();
        this.getChildren().add(mainGrid);

        //Top bar
        double topBarHeight = sceneHeight * 0.065;
        topSide = new MeetingTopBar(stage, this, topBarHeight , this.getPrefWidth());
        topSide.setPrefHeight(topBarHeight);
        topSide.setMaxHeight(topBarHeight);
        topSide.setMinHeight(topBarHeight);
        mainGrid.setTop(topSide);

        //Participant Tab
        participantTab = new MeetingParticipantsBar();
        participantTab.hide();
        mainGrid.setRight(participantTab);

        //PDF Viewer
        pdfViewer = new PDFViewer(pdfViewerObserver ,stage , this , sceneWidth,sceneHeight * 0.935);
        mainGrid.setCenter(pdfViewer);

        this.sceneHeight = sceneHeight;

        //AuthLayoutController
        authLayoutController = new AuthLayoutController(UserType.HOST_PRESENTER,this);

        //Network Manager
        networkManager = new NetworkManager(authLayoutController,pdfViewer);

        //PDF Viewer Observer
        pdfViewerObserver = new PDFViewerObserver(networkManager);
    }

    public void setResponsiveWidth(double width) {
        double pdfViewerWidth = width - participantTab.getPrefWidth();
        pdfViewer.setPrefWidth(pdfViewerWidth);
    }

    public void setResponsiveHeight(double height) {
        double pdfViewerHeight = height * 0.92;
        pdfViewer.setPrefHeight(pdfViewerHeight);
    }

    public void showHideParticipantsTab() {
        if (participantTab.isShown()) {
            hideParticipantsTab();
        }
        else {
            showParticipantsTab();
        }
    }

    private void hideParticipantsTab() {
        participantTab.hide();
        pdfViewer.setPrefWidth(pdfViewer.getPrefWidth() + participantTab.getSideBarWidth());
    }

    private void showParticipantsTab() {
        participantTab.show();
        pdfViewer.setPrefWidth(pdfViewer.getPrefWidth() - participantTab.getSideBarWidth());
    }

    public MeetingTopBar getTopSide() {
        return topSide;
    }

    public MeetingParticipantsBar getParticipantTab() {
        return participantTab;
    }

    public PDFViewer getPdfViewer() {
        return pdfViewer;
    }
}
