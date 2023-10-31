package com.harun.liveSlide;

import com.harun.liveSlide.components.meetingTopBar.MeetingTopBar;
import com.harun.liveSlide.components.pdfViewer.PDFViewer;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Pane {

    public MeetingTopBar topSide;
    private Pane leftSide;
    private Pane rightSide;

    public PDFViewer pdfViewer;
    public double sceneHeight;

    public MainWindow(Stage stage , double sceneWidth , double sceneHeight) throws IOException {
        BorderPane mainGrid = new BorderPane();
        this.getChildren().add(mainGrid);
        topSide = new MeetingTopBar();
        topSide.setPrefHeight(sceneHeight * 0.08);
        mainGrid.setTop(topSide);

        leftSide = new Pane();
        leftSide.setPrefWidth(0);
        mainGrid.setLeft(leftSide);

        rightSide = new Pane();
        rightSide.setPrefWidth(0);
        mainGrid.setRight(rightSide);

        pdfViewer = new PDFViewer(stage , this , sceneWidth,sceneHeight * 0.92);
        mainGrid.setCenter(pdfViewer);

        this.sceneHeight = sceneHeight;
    }

    public void setResponsiveWidth(double width) {
        double pdfViewerWidth = width;
        pdfViewer.setPrefWidth(pdfViewerWidth);
    }

    public void setResponsiveHeight(double height) {
        double pdfViewerHeight = height * 0.92;
        pdfViewer.setPrefHeight(pdfViewerHeight);
    }
}
