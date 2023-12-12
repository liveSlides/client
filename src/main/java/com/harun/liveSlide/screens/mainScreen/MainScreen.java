package com.harun.liveSlide.screens.mainScreen;

import com.harun.liveSlide.components.meetingSideBar.MeetingParticipantsBar;
import com.harun.liveSlide.components.meetingTopBar.MeetingTopBar;
import com.harun.liveSlide.components.pdfViewer.PDFViewer;
import com.harun.liveSlide.components.pdfViewer.PDFViewerObserver;
import com.harun.liveSlide.enums.UserType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreen extends Pane {

    public MeetingTopBar topSide;
    private MeetingParticipantsBar participantTab;

    public PDFViewer pdfViewer;
    public PDFViewerObserver pdfViewerObserver;
    public AuthLayoutController authLayoutController;
    public double sceneHeight;

    public MainScreen(Stage stage , double sceneWidth , double sceneHeight) throws IOException {
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

        //PDF Viewer Observer
        pdfViewerObserver = new PDFViewerObserver();
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
