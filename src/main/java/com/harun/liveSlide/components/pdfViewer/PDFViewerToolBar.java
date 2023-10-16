package com.harun.liveSlide.components.pdfViewer;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Objects;

public class PDFViewerToolBar extends ToolBar {
    private PDFViewerNavigationController pdfViewerNavigationController;
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

    public PDFViewerToolBar(double prefHeight , PDFViewerNavigationController pdfViewerNavigationController) {
        this.pdfViewerNavigationController = pdfViewerNavigationController;
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
        backButton = new Button();
        backButton.setOnAction(pdfViewerNavigationController::goBackPage);
        backButton.setDisable(true);
        backButton.setGraphic(getButtonIcon("/img/back.png", prefHeight));
        backButton.setTooltip(new Tooltip("Go previous page"));
        this.getItems().add(backButton);

        // Page Indicator
        pageIndicator = new Label();
        this.getItems().add(pageIndicator);

        // Next Button
        nextButton = new Button();
        nextButton.setOnAction(pdfViewerNavigationController::goNextPage);
        nextButton.setGraphic(getButtonIcon("/img/next.png", prefHeight));
        nextButton.setTooltip(new Tooltip("Go next page"));
        this.getItems().add(nextButton);

        //Divider 1
        SplitPane divider1 = new SplitPane();
        this.getItems().add(divider1);

        //Zoom Out Button
        zoomOutButton = new Button();
        zoomOutButton.setGraphic(getButtonIcon("/img/zoom-out.png", prefHeight));
        zoomOutButton.setTooltip(new Tooltip("Zoom Out"));
        this.getItems().add(zoomOutButton);

        //Zoom Rate Label
        zoomRateLabel = new Label("100%");
        this.getItems().add(zoomRateLabel);

        //Zoom In Button
        zoomInButton = new Button();
        zoomInButton.setGraphic(getButtonIcon("/img/zoom-in.png", prefHeight));
        zoomInButton.setTooltip(new Tooltip("Zoom In"));
        this.getItems().add(zoomInButton);

        //Divider 2
        SplitPane divider2 = new SplitPane();
        this.getItems().add(divider2);

        //Rotate Button
        rotateButton = new Button();
        rotateButton.setGraphic(getButtonIcon("/img/rotate.png", prefHeight));
        rotateButton.setTooltip(new Tooltip("Rotate"));
        this.getItems().add(rotateButton);

        //Divider 3
        SplitPane divider3 = new SplitPane();
        this.getItems().add(divider3);

        //Pointer Button
        pointerButton = new Button();
        pointerButton.setGraphic(getButtonIcon("/img/pointer.png", prefHeight));
        pointerButton.setTooltip(new Tooltip("Pointer"));
        this.getItems().add(pointerButton);

        //Draw Button
        drawButton = new Button();
        drawButton.setGraphic(getButtonIcon("/img/pen.png", prefHeight));
        drawButton.setTooltip(new Tooltip("Draw"));
        this.getItems().add(drawButton);

        //Right Spacer
        Pane rightSpacer = new Pane();
        HBox.setHgrow(
                rightSpacer,
                Priority.SOMETIMES
        );
        this.getItems().add(rightSpacer);

        //Fullscreen Button
        fullscreenButton = new Button();
        fullscreenButton.setGraphic(getButtonIcon("/img/fullscreen.png", prefHeight));
        fullscreenButton.setTooltip(new Tooltip("Full Screen"));
        this.getItems().add(fullscreenButton);

        //Download Button
        downloadButton = new Button();
        downloadButton.setGraphic(getButtonIcon("/img/download.png", prefHeight));
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
    private ImageView getButtonIcon(String path , double prefHeight) {
        ImageView iconImage = new ImageView(new Image(String.valueOf(getClass().getResource(path))));
        iconImage.setFitWidth(prefHeight / 1.5);
        iconImage.setFitHeight(prefHeight / 1.5);
        return iconImage;
    }
}
