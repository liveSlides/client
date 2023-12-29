package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.screens.mainScreen.MainScreen;
import com.harun.liveSlide.model.MouseCoordinate;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PDFViewer extends BorderPane {
    private PDFViewerObserver pdfViewerObserver;
    private final PDFViewerZoomController pdfViewerZoomController;
    private final PDFViewerScrollController pdfViewerScrollController;
    private final PDFViewerRotateController pdfViewerRotateController;
    private final PDFViewerDrawController pdfViewerDrawController;
    private final PDFViewerPointerController pdfViewerPointerController;
    private final PDFViewerNavigationController pdfViewerNavigationController;
    private final PDFViewerFileController pdfViewerFileController;
    private final PDFViewerToolController pdfViewerToolController;
    public ScrollPane viewArea;
    public PDFViewerToolBar toolBar;
    public ArrayList<PDFPageContainer> pdfPages;
    public String currentFilePath;
    private final Stage stage;
    private final MainScreen mainScreen;

    public PDFViewer(Stage stage , MainScreen mainScreen, double prefWidth, double prefHeight) {
        this.setId("pdf-viewer");
        this.pdfViewerZoomController = new PDFViewerZoomController(this);
        this.pdfViewerScrollController = new PDFViewerScrollController();
        this.pdfViewerRotateController = new PDFViewerRotateController(this);
        this.pdfViewerDrawController = new PDFViewerDrawController(this);
        this.pdfViewerPointerController = new PDFViewerPointerController(this);
        this.pdfViewerNavigationController = new PDFViewerNavigationController(this,0);
        this.pdfViewerFileController = new PDFViewerFileController(this);
        this.pdfViewerToolController = new PDFViewerToolController(this);
        this.stage = stage;
        this.mainScreen = mainScreen;
        setupLayout(prefWidth,prefHeight);

        // If esc is pressed then exit fullscreen with our function
        stage.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                pdfViewerToolController.setFullScreen(false);
                event.consume();
            }
        });
    }

    public PDFViewer(PDFViewerObserver pdfViewerObserver , Stage stage , MainScreen mainScreen, double prefWidth, double prefHeight) {
        this(stage, mainScreen,prefWidth,prefHeight);
        this.pdfViewerObserver = pdfViewerObserver;
    }

    private void setupLayout(double prefWidth , double prefHeight){
        setPrefSize(prefWidth, prefHeight);

        // Tool Bar
        toolBar = new PDFViewerToolBar(30,this);
        toolBar.setId("pdf-viewer-toolbar");
        this.setTop(toolBar);

        // View Area
        viewArea = new ScrollPane();
        viewArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        viewArea.setOnZoom((ZoomEvent event) -> {
            if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER){
                double zoomFactor = event.getZoomFactor();
                zoom(zoomFactor > 1 ? 2 : 0);
                event.consume();
            }
        });

        viewArea.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    goBackPage();
                    break;
                case RIGHT:
                    goNextPage();
                    break;
                default:
                    break;
            }
        });

        viewArea.vvalueProperty().addListener((observable, oldValue, newValue) -> {pdfViewerObserver.scrolledVerticallyTo((Double) newValue);});
        viewArea.hvalueProperty().addListener((observable, oldValue, newValue) -> {pdfViewerObserver.scrolledHorizontallyTo((Double) newValue);});

        if (GlobalVariables.userType == UserType.PARTICIPANT_SPECTATOR || GlobalVariables.userType == UserType.HOST_SPECTATOR) {
            viewArea.addEventFilter(ScrollEvent.SCROLL, Event::consume);
            viewArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            viewArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }
        this.setCenter(viewArea);

        setViewAreaToControllers();
    }

    private void setViewAreaToControllers(){
        pdfViewerScrollController.setViewArea(viewArea);
        pdfViewerDrawController.setViewArea(viewArea);
    }

    public void goPage(int index) {
        pdfViewerNavigationController.goPage(index);
        pdfViewerObserver.pageChanged(index);
    }

    public void goBackPage() {
        if (pdfViewerNavigationController.goBackPage()){
            int index = pdfViewerNavigationController.getCurrentPageIndex();
            pdfViewerObserver.pageChanged(index);
        }
    }

    public void goNextPage() {
        if (pdfViewerNavigationController.goNextPage()) {
            int index = pdfViewerNavigationController.getCurrentPageIndex();
            pdfViewerObserver.pageChanged(index);
        }
    }

    public void zoom(double zoomFactor) {
        pdfViewerZoomController.zoom(zoomFactor);
        pdfViewerObserver.zoomed(pdfViewerZoomController.getCurrentPdfPage().getCurrentZoomRate());
    }

    public void zoomToZoomRate(int zoomRate) {
        pdfViewerZoomController.zoomToZoomRate(zoomRate);
        pdfViewerObserver.zoomed(pdfViewerZoomController.getCurrentPdfPage().getCurrentZoomRate());
    }

    public void scrollVerticallyTo(double vValue) {
        pdfViewerScrollController.scrollVerticallyTo(vValue);
        pdfViewerObserver.scrolledVerticallyTo(vValue);
    }

    public void scrollHorizontallyTo(double hValue) {
        pdfViewerScrollController.scrollHorizontallyTo(hValue);
        pdfViewerObserver.scrolledHorizontallyTo(hValue);
    }

    public void rotate() {
        pdfViewerRotateController.rotate();
        pdfViewerObserver.rotated(pdfViewerRotateController.getRotateRate());
    }

    public void rotateToRotateRate(int rotateRate) {
        pdfViewerRotateController.rotateToRotateRate(rotateRate);
        pdfViewerObserver.rotated(pdfViewerRotateController.getRotateRate());
    }

    public void canvasPressed(MouseCoordinate mouseCoordinate) {
        pdfViewerDrawController.onMousePressed(mouseCoordinate.x,mouseCoordinate.y);
        pdfViewerObserver.canvasPressed(mouseCoordinate);
    }

    public void canvasDragged(MouseCoordinate mouseCoordinate) {
        pdfViewerDrawController.onMouseDragged(mouseCoordinate.x,mouseCoordinate.y);
        pdfViewerObserver.canvasDragged(mouseCoordinate);
    }

    public void pointByPresenter(double x , double y) {
        if (pdfViewerToolController.getCurrentPdfTool() == PDFTool.POINTER &&
                GlobalVariables.userType == UserType.HOST_PRESENTER ||
                GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER) {
            pdfViewerPointerController.point(x ,y);
            pdfViewerObserver.pointed(x,y);
        }
    }

    public void point(double x , double y) {
        if (pdfViewerToolController.getCurrentPdfTool() == PDFTool.POINTER) {
            pdfViewerPointerController.point(x ,y);
            pdfViewerObserver.pointed(x,y);
        }
    }

    public void showFullScreen(boolean isShowFullScreen) {

        stage.setFullScreen(isShowFullScreen);
        if (isShowFullScreen) {
            mainScreen.topSide.setPrefHeight(0);
            mainScreen.topSide.setMinHeight(0);
            mainScreen.topSide.setMaxHeight(0);
            mainScreen.pdfViewer.setPrefHeight(mainScreen.sceneHeight * 1.052);
        }
        else {
            mainScreen.topSide.setPrefHeight(mainScreen.sceneHeight * 0.065);
            mainScreen.topSide.setMinHeight(mainScreen.sceneHeight * 0.065);
            mainScreen.topSide.setMaxHeight(mainScreen.sceneHeight * 0.065);
            mainScreen.pdfViewer.setPrefHeight(mainScreen.sceneHeight * 0.935);
        }
    }

    public void changeActiveTool(PDFTool tool) {
        pdfViewerToolController.setCurrentPdfTool(tool);
        pdfViewerObserver.activeToolChanged(tool);
    }

    public void changeActivePenSize(PenEraserSize size) {
        pdfViewerToolController.setCurrentDrawSize(size);
        pdfViewerObserver.penSizeChanged(size);
    }

    public void changeActivePenColor(PenColor penColor) {
        pdfViewerToolController.setCurrentDrawColor(penColor);
        pdfViewerObserver.penColorChanged(penColor);
    }

    public void changeActiveEraserSize(PenEraserSize size) {
        pdfViewerToolController.setCurrentEraserSize(size);
        pdfViewerObserver.eraserSizeChanged(size);
    }

    public void notifyLoadPdf(String path) {
        pdfViewerObserver.loadedPDF(path);
    }

    public void loadPDF(String path) {
        pdfViewerFileController.loadPDF(path , 1 , true);
    }

    public void loadPDF(String path , int index) throws IOException {
        pdfViewerFileController.loadPDF(path , index , false);
    }

    public void setToolsVisible(boolean visibility) {
        toolBar.setToolsVisible(visibility);
    }

    public void reloadGraphicsContext(){
        pdfViewerDrawController.reloadGraphicsContext();
    }
    public void reloadPointerShape() {
        pdfViewerPointerController.updatePointerShape();
    }

    public PDFViewerDrawController getPdfViewerDrawController() {
        return pdfViewerDrawController;
    }

    public PDFViewerNavigationController getPdfViewerNavigationController() {
        return pdfViewerNavigationController;
    }

    public PDFViewerFileController getPdfViewerFileController() {
        return pdfViewerFileController;
    }

    public PDFViewerToolController getPdfViewerToolController() {
        return pdfViewerToolController;
    }

    public PDFViewerPointerController getPdfViewerPointerController() {
        return pdfViewerPointerController;
    }



}
