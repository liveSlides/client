package com.harun.liveSlide;

import com.harun.liveSlide.components.pdfViewer.PDFViewer;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainWindow extends Pane {

    private Pane topSide;
    private Pane leftSide;
    private Pane rightSide;
    public PDFViewer pdfViewer;

    public MainWindow() throws IOException {
        BorderPane mainGrid = new BorderPane();
        this.getChildren().add(mainGrid);
        
        topSide = new Pane();
        topSide.setPrefHeight(80);
        mainGrid.setTop(topSide);

        leftSide = new Pane();
        leftSide.setPrefWidth(0);
        mainGrid.setLeft(leftSide);

        rightSide = new Pane();
        rightSide.setPrefWidth(0);
        mainGrid.setRight(rightSide);

        pdfViewer = new PDFViewer(1680,920);
        mainGrid.setCenter(pdfViewer);

    }
}
