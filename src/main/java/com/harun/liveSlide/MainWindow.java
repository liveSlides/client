package com.harun.liveSlide;

import com.harun.liveSlide.components.meetingSideBar.MeetingSideBar;
import com.harun.liveSlide.components.meetingTopBar.MeetingTopBar;
import com.harun.liveSlide.components.pdfViewer.PDFViewer;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Pane {

    public MeetingTopBar topSide;
    private Pane leftSide;
    private MeetingSideBar participantTab;

    public PDFViewer pdfViewer;
    public double sceneHeight;

    public MainWindow(Stage stage , double sceneWidth , double sceneHeight) throws IOException {
        this.getStyleClass().add("main-window");

        BorderPane mainGrid = new BorderPane();
        this.getChildren().add(mainGrid);
        double prefHeight = sceneHeight * 0.065;
        topSide = new MeetingTopBar(stage, this, prefHeight);
        topSide.setPrefHeight(prefHeight);
        topSide.setMaxHeight(prefHeight);
        topSide.setMinHeight(prefHeight);
        mainGrid.setTop(topSide);

        leftSide = new Pane();
        leftSide.setPrefWidth(0);
        mainGrid.setLeft(leftSide);

        participantTab = new MeetingSideBar();
        participantTab.setPrefWidth(0);
        mainGrid.setRight(participantTab);

        pdfViewer = new PDFViewer(stage , this , sceneWidth,sceneHeight * 0.935);
        mainGrid.setCenter(pdfViewer);

        this.sceneHeight = sceneHeight;
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
}
