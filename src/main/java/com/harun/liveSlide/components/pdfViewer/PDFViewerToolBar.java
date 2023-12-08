package com.harun.liveSlide.components.pdfViewer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Objects;

public class PDFViewerToolBar extends ToolBar {
    private PDFViewer pdfViewer;
    private Label pdfTitle;
    private Button changePdfButton;
    private Label pageIndicator;
    private Button backButton;
    private Button nextButton;
    private Button zoomOutButton;
    private Button zoomInButton;
    private Label zoomRateLabel;
    private Button rotateButton;
    private Button cursorButton;
    private Button pointerButton;
    private Button drawButton;
    private Button eraserButton;
    private Button sizeDrawSmallButton;
    private Button sizeDrawMediumButton;
    private Button sizeDrawBigButton;
    private Button sizeEraserSmallButton;
    private Button sizeEraserMediumButton;
    private Button sizeEraserBigButton;
    private Button colorBlackButton;
    private Button colorRedButton;
    private Button colorBlueButton;
    private Button fullscreenButton;
    private SplitPane divider4;
    private SplitPane divider5;
    private Pane rightSpacer;


    public PDFViewerToolBar(double prefHeight , PDFViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
        setupLayout(prefHeight);
    }

    public void setupLayout(double prefHeight) {
        this.setPrefHeight(prefHeight);
        this.setPadding(new Insets(6,10,7,10));

        //PDF Title
        pdfTitle = new Label("Select a File");
        this.getItems().add(pdfTitle);

        //PDF Change Button
        changePdfButton = new Button();
        changePdfButton.setGraphic(getButtonIcon("/img/changePDF.png", prefHeight));
        changePdfButton.setOnAction(pdfViewer.getPdfViewerFileController()::openFileSelection);
        this.getItems().add(changePdfButton);

        //Left Spacer
        Pane leftSpacer = new Pane();
        HBox.setHgrow(
                leftSpacer,
                Priority.SOMETIMES
        );
        this.getItems().add(leftSpacer);

        //Back Button
        backButton = new Button();
        backButton.setOnAction(e -> {pdfViewer.goBackPage();});
        backButton.setDisable(true);
        backButton.setGraphic(getButtonIcon("/img/back.png", prefHeight));
        backButton.setTooltip(new Tooltip("Go previous page"));
        this.getItems().add(backButton);
        backButton.setAlignment(Pos.CENTER);

        // Page Indicator
        pageIndicator = new Label();
        this.getItems().add(pageIndicator);

        // Next Button
        nextButton = new Button();
        nextButton.setOnAction(e -> {pdfViewer.goNextPage();});
        nextButton.setGraphic(getButtonIcon("/img/next.png", prefHeight));
        nextButton.setTooltip(new Tooltip("Go next page"));
        this.getItems().add(nextButton);

        //Divider 1
        SplitPane divider1 = new SplitPane();
        this.getItems().add(divider1);

        //Zoom Out Button
        zoomOutButton = new Button();
        zoomOutButton.setOnAction(pdfViewer.getPdfViewerZoomController()::zoomOut);
        zoomOutButton.setGraphic(getButtonIcon("/img/zoom-out.png", prefHeight));
        zoomOutButton.setTooltip(new Tooltip("Zoom Out"));
        this.getItems().add(zoomOutButton);

        //Zoom Rate Label
        zoomRateLabel = new Label("100%");
        this.getItems().add(zoomRateLabel);

        //Zoom In Button
        zoomInButton = new Button();
        zoomInButton.setOnAction(pdfViewer.getPdfViewerZoomController()::zoomIn);
        zoomInButton.setGraphic(getButtonIcon("/img/zoom-in.png", prefHeight));
        zoomInButton.setTooltip(new Tooltip("Zoom In"));
        this.getItems().add(zoomInButton);

        //Divider 2
        SplitPane divider2 = new SplitPane();
        this.getItems().add(divider2);

        //Rotate Button
        rotateButton = new Button();
        rotateButton.setOnAction(pdfViewer.getPdfViewerRotateController()::rotate);
        rotateButton.setGraphic(getButtonIcon("/img/rotate.png", prefHeight));
        rotateButton.setTooltip(new Tooltip("Rotate"));
        this.getItems().add(rotateButton);

        //Divider 3
        SplitPane divider3 = new SplitPane();
        this.getItems().add(divider3);

        //Cursor Button
        cursorButton = new Button();
        cursorButton.setOnAction(pdfViewer.getPdfViewerToolController()::cursor);
        cursorButton.setGraphic(getButtonIcon("/img/cursor.png", prefHeight));
        cursorButton.setTooltip(new Tooltip("Pan"));
        this.getItems().add(cursorButton);

        //Pointer Button
        pointerButton = new Button();
        pointerButton.setOnAction(pdfViewer.getPdfViewerToolController()::pointer);
        pointerButton.setGraphic(getButtonIcon("/img/pointer.png", prefHeight));
        pointerButton.setTooltip(new Tooltip("Laser Pointer"));
        this.getItems().add(pointerButton);

        //Draw Button
        drawButton = new Button();
        drawButton.setOnAction(pdfViewer.getPdfViewerToolController()::draw);
        drawButton.setGraphic(getButtonIcon("/img/pen.png", prefHeight));
        drawButton.setTooltip(new Tooltip("Draw"));
        this.getItems().add(drawButton);

        //Eraser Button
        eraserButton = new Button();
        eraserButton.setOnAction(pdfViewer.getPdfViewerToolController()::eraser);
        eraserButton.setGraphic(getButtonIcon("/img/eraser.png", prefHeight));
        eraserButton.setTooltip(new Tooltip("Erase"));
        this.getItems().add(eraserButton);

        //Divider 4
        divider4 = new SplitPane();

        //Size Draw Small Button
        sizeDrawSmallButton = new Button();
        sizeDrawSmallButton.setOnAction(pdfViewer.getPdfViewerToolController()::setDrawSizeSmall);
        sizeDrawSmallButton.setGraphic(getButtonIcon("/img/smallDot.png", prefHeight / 2.5));
        sizeDrawSmallButton.setTooltip(new Tooltip("Small"));

        //Size Draw Medium Button
        sizeDrawMediumButton = new Button();
        sizeDrawMediumButton.setOnAction(pdfViewer.getPdfViewerToolController()::setDrawSizeMedium);
        sizeDrawMediumButton.setGraphic(getButtonIcon("/img/mediumDot.png", prefHeight / 1.5));
        sizeDrawMediumButton.setTooltip(new Tooltip("Medium"));

        //Size Draw Big Button
        sizeDrawBigButton = new Button();
        sizeDrawBigButton.setOnAction(pdfViewer.getPdfViewerToolController()::setDrawSizeBig);
        sizeDrawBigButton.setGraphic(getButtonIcon("/img/bigDot.png", prefHeight));
        sizeDrawBigButton.setTooltip(new Tooltip("Big"));

        //Size Eraser Small Button
        sizeEraserSmallButton = new Button();
        sizeEraserSmallButton.setOnAction(pdfViewer.getPdfViewerToolController()::setEraserSizeSmall);
        sizeEraserSmallButton.setGraphic(getButtonIcon("/img/smallDot.png", prefHeight / 2.5));
        sizeEraserSmallButton.setTooltip(new Tooltip("Small"));

        //Size Eraser Medium Button
        sizeEraserMediumButton = new Button();
        sizeEraserMediumButton.setOnAction(pdfViewer.getPdfViewerToolController()::setEraserSizeMedium);
        sizeEraserMediumButton.setGraphic(getButtonIcon("/img/mediumDot.png", prefHeight / 1.5));
        sizeEraserMediumButton.setTooltip(new Tooltip("Medium"));

        //Size Eraser Big Button
        sizeEraserBigButton = new Button();
        sizeEraserBigButton.setOnAction(pdfViewer.getPdfViewerToolController()::setEraserSizeBig);
        sizeEraserBigButton.setGraphic(getButtonIcon("/img/bigDot.png", prefHeight));
        sizeEraserBigButton.setTooltip(new Tooltip("Big"));

        //Divider 5
        divider5 = new SplitPane();

        //Color Black Button
        colorBlackButton = new Button();
        colorBlackButton.setOnAction(pdfViewer.getPdfViewerToolController()::setColorBlack);
        colorBlackButton.setGraphic(getButtonIcon("/img/drawBlack.png", prefHeight));
        colorBlackButton.setTooltip(new Tooltip("Black"));

        //Color Red Button
        colorRedButton = new Button();
        colorRedButton.setOnAction(pdfViewer.getPdfViewerToolController()::setColorRed);
        colorRedButton.setGraphic(getButtonIcon("/img/drawRed.png", prefHeight));
        colorRedButton.setTooltip(new Tooltip("Red"));

        //Color Blue Button
        colorBlueButton = new Button();
        colorBlueButton.setOnAction(pdfViewer.getPdfViewerToolController()::setColorBlue);
        colorBlueButton.setGraphic(getButtonIcon("/img/drawBlue.png", prefHeight));
        colorBlueButton.setTooltip(new Tooltip("Blue"));

        //Right Spacer
        rightSpacer = new Pane();
        HBox.setHgrow(
                rightSpacer,
                Priority.SOMETIMES
        );
        this.getItems().add(rightSpacer);

        //Fullscreen Button
        fullscreenButton = new Button();
        fullscreenButton.setOnAction(pdfViewer.getPdfViewerToolController()::showFullScreen);
        fullscreenButton.setGraphic(getButtonIcon("/img/fullscreen.png", prefHeight));
        fullscreenButton.setTooltip(new Tooltip("Full Screen"));
        this.getItems().add(fullscreenButton);

        //Download Button
        /*
        downloadButton = new Button();
        downloadButton.setGraphic(getButtonIcon("/img/download.png", prefHeight));
        this.getItems().add(downloadButton);*/
    }

    public void setPdfTitleText(String text) {
        if (text.length() > 8) {
            text = text.substring(0,8);
            text = text + "...";
            this.pdfTitle.setText(text);
        }
        else{
            this.pdfTitle.setText(text);
        }
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

    public void updatePageIndicatorLabelText(int pageIndex , int pageCount) {
        StringBuilder sb = new StringBuilder();
        sb.append(pageIndex);
        sb.append(" / ");
        sb.append(pageCount);
        this.setPageIndicatorText(sb.toString());
    }

    public void updateZoomRateLabelText(int rate) {
        this.zoomRateLabel.setText(String.valueOf(rate) + "%");
    }

    public void setCursorSelected() {
        pointerButton.getStyleClass().remove("selected-tool-button");
        drawButton.getStyleClass().remove("selected-tool-button");
        eraserButton.getStyleClass().remove("selected-tool-button");
        cursorButton.getStyleClass().add("selected-tool-button");
        pdfViewer.getPdfViewerPointerController().isPointerVisible(false);

        removeSizeAndColorButtons();
        this.getItems().add(rightSpacer);
        this.getItems().add(fullscreenButton);
    }

    public void setPointerSelected() {
        drawButton.getStyleClass().remove("selected-tool-button");
        cursorButton.getStyleClass().remove("selected-tool-button");
        eraserButton.getStyleClass().remove("selected-tool-button");
        pointerButton.getStyleClass().add("selected-tool-button");
        pdfViewer.getPdfViewerPointerController().isPointerVisible(true);

        removeSizeAndColorButtons();
        this.getItems().add(rightSpacer);
        this.getItems().add(fullscreenButton);
    }

    public void setDrawSelected() {
        cursorButton.getStyleClass().remove("selected-tool-button");
        pointerButton.getStyleClass().remove("selected-tool-button");
        eraserButton.getStyleClass().remove("selected-tool-button");
        drawButton.getStyleClass().add("selected-tool-button");
        pdfViewer.getPdfViewerPointerController().isPointerVisible(false);

        removeSizeAndColorButtons();
        this.getItems().add(divider4);
        this.getItems().add(sizeDrawSmallButton);
        this.getItems().add(sizeDrawMediumButton);
        this.getItems().add(sizeDrawBigButton);
        this.getItems().add(divider5);
        this.getItems().add(colorBlackButton);
        this.getItems().add(colorRedButton);
        this.getItems().add(colorBlueButton);
        this.getItems().add(rightSpacer);
        this.getItems().add(fullscreenButton);
    }

    public void setEraserSelected() {
        cursorButton.getStyleClass().remove("selected-tool-button");
        pointerButton.getStyleClass().remove("selected-tool-button");
        drawButton.getStyleClass().remove("selected-tool-button");
        eraserButton.getStyleClass().add("selected-tool-button");
        pdfViewer.getPdfViewerPointerController().isPointerVisible(false);

        removeSizeAndColorButtons();
        this.getItems().add(divider4);
        this.getItems().add(sizeEraserSmallButton);
        this.getItems().add(sizeEraserMediumButton);
        this.getItems().add(sizeEraserBigButton);
        this.getItems().add(rightSpacer);
        this.getItems().add(fullscreenButton);
    }

    private void removeSizeAndColorButtons() {
        this.getItems().remove(sizeDrawSmallButton);
        this.getItems().remove(sizeDrawMediumButton);
        this.getItems().remove(sizeDrawBigButton);
        this.getItems().remove(sizeEraserSmallButton);
        this.getItems().remove(sizeEraserMediumButton);
        this.getItems().remove(sizeEraserBigButton);
        this.getItems().remove(colorBlackButton);
        this.getItems().remove(colorRedButton);
        this.getItems().remove(colorBlueButton);
        this.getItems().remove(divider4);
        this.getItems().remove(divider5);
        this.getItems().remove(rightSpacer);
        this.getItems().remove(fullscreenButton);
    }

    public void drawSmallSizeSelected() {
        sizeDrawBigButton.getStyleClass().remove("selected-tool-button");
        sizeDrawMediumButton.getStyleClass().remove("selected-tool-button");
        sizeDrawSmallButton.getStyleClass().add("selected-tool-button");
    }

    public void drawMediumSizeSelected() {
        sizeDrawBigButton.getStyleClass().remove("selected-tool-button");
        sizeDrawSmallButton.getStyleClass().remove("selected-tool-button");
        sizeDrawMediumButton.getStyleClass().add("selected-tool-button");
    }

    public void drawBigSizeSelected() {
        sizeDrawMediumButton.getStyleClass().remove("selected-tool-button");
        sizeDrawSmallButton.getStyleClass().remove("selected-tool-button");
        sizeDrawBigButton.getStyleClass().add("selected-tool-button");
    }

    public void eraserSmallSizeSelected() {
        sizeEraserBigButton.getStyleClass().remove("selected-tool-button");
        sizeEraserMediumButton.getStyleClass().remove("selected-tool-button");
        sizeEraserSmallButton.getStyleClass().add("selected-tool-button");
    }

    public void eraserMediumSizeSelected() {
        sizeEraserBigButton.getStyleClass().remove("selected-tool-button");
        sizeEraserSmallButton.getStyleClass().remove("selected-tool-button");
        sizeEraserMediumButton.getStyleClass().add("selected-tool-button");
    }

    public void eraserBigSizeSelected() {
        sizeEraserMediumButton.getStyleClass().remove("selected-tool-button");
        sizeEraserSmallButton.getStyleClass().remove("selected-tool-button");
        sizeEraserBigButton.getStyleClass().add("selected-tool-button");
    }

    public void drawBlackSelected() {
        colorRedButton.getStyleClass().remove("selected-tool-button");
        colorBlueButton.getStyleClass().remove("selected-tool-button");
        colorBlackButton.getStyleClass().add("selected-tool-button");
    }

    public void drawRedSelected() {
        colorBlackButton.getStyleClass().remove("selected-tool-button");
        colorBlueButton.getStyleClass().remove("selected-tool-button");
        colorRedButton.getStyleClass().add("selected-tool-button");
    }

    public void drawBlueSelected() {
        colorBlackButton.getStyleClass().remove("selected-tool-button");
        colorRedButton.getStyleClass().remove("selected-tool-button");
        colorBlueButton.getStyleClass().add("selected-tool-button");
    }

    private ImageView getButtonIcon(String path , double prefHeight) {
        ImageView iconImage = new ImageView(new Image(String.valueOf(getClass().getResource(path))));
        iconImage.setFitWidth(prefHeight / 1.5);
        iconImage.setFitHeight(prefHeight / 1.5);
        return iconImage;
    }


}
