package com.harun.liveSlide.components.pdfViewer;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class PDFViewerToolBar extends ToolBar {
    private PDFViewer pdfViewer;
    private Label pdfTitle;
    private Label pageIndicator;
    private Button backButton;
    private Button nextButton;
    private Button zoomOutButton;
    private Button zoomInButton;
    private Label zoomRateLabel;
    private Button rotateButton;
    private Button pointerButton;
    private Button drawButton;
    private Button fullscreenButton;
    private Button downloadButton;

    public PDFViewerToolBar(double prefHeight , PDFViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
        setupLayout(prefHeight);
    }

    public void setupLayout(double prefHeight) {
        this.setPrefHeight(prefHeight);

        //PDF Title
        pdfTitle = new Label("pdf nameeeeee");
        this.getItems().add(pdfTitle);

        //Left Spacer
        Pane leftSpacer = new Pane();
        HBox.setHgrow(
                leftSpacer,
                Priority.SOMETIMES
        );
        this.getItems().add(leftSpacer);

        //Back Button
        backButton = new Button("Back");
        backButton.setOnAction(pdfViewer.pdfViewerNavigationController::goBackPage);
        backButton.setDisable(true);
        this.getItems().add(backButton);

        // Page Indicator
        pageIndicator = new Label();
        this.getItems().add(pageIndicator);

        // Next Button
        nextButton = new Button("Next");
        nextButton.setOnAction(pdfViewer.pdfViewerNavigationController::goNextPage);
        this.getItems().add(nextButton);

        //Divider 1
        SplitPane divider1 = new SplitPane();
        this.getItems().add(divider1);

        //Zoom Out Button
        zoomOutButton = new Button("Zoom Out");
        this.getItems().add(zoomOutButton);

        //Zoom Rate Label
        zoomRateLabel = new Label("100%");
        this.getItems().add(zoomRateLabel);

        //Zoom In Button
        zoomInButton = new Button("Zoom In");
        this.getItems().add(zoomInButton);

        //Divider 2
        SplitPane divider2 = new SplitPane();
        this.getItems().add(divider2);

        //Rotate Button
        rotateButton = new Button("Rotate");
        this.getItems().add(rotateButton);

        //Divider 3
        SplitPane divider3 = new SplitPane();
        this.getItems().add(divider3);

        //Pointer Button
        pointerButton = new Button("Pointer");
        this.getItems().add(pointerButton);

        //Draw Button
        drawButton = new Button("Draw");
        this.getItems().add(drawButton);

        //Right Spacer
        Pane rightSpacer = new Pane();
        HBox.setHgrow(
                rightSpacer,
                Priority.SOMETIMES
        );
        this.getItems().add(rightSpacer);

        //Fullscreen Button
        fullscreenButton = new Button("Fullscreen");
        this.getItems().add(fullscreenButton);

        //Download Button
        downloadButton = new Button("Download");
        this.getItems().add(downloadButton);
    }

    public void setPdfTitleText(String text) {
        this.pdfTitle.setText(text);
    }

    public void setPageIndicatorText(String text) {
        this.pageIndicator.setText(text);
    }

    public void setDisableBackButton(boolean status) {
        this.backButton.setDisable(status);
    }

    public void setDisableNextButton(boolean status) {
        this.nextButton.setDisable(status);
    }

    public void updatePageIndicator(int pageIndex , int pageCount) {
        StringBuilder sb = new StringBuilder();
        sb.append(pageIndex);
        sb.append(" / ");
        sb.append(pageCount);
        this.setPageIndicatorText(sb.toString());
    }
}
