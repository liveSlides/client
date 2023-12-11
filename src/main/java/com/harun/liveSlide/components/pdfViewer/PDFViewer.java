package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.MainWindow;
import com.harun.liveSlide.model.MouseCoordinate;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
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
    private final MainWindow mainWindow;

    public PDFViewer(Stage stage , MainWindow mainWindow , double prefWidth, double prefHeight) {
        this.setId("pdf-viewer");
        this.pdfViewerZoomController = new PDFViewerZoomController(this);
        this.pdfViewerScrollController = new PDFViewerScrollController();
        this.pdfViewerRotateController = new PDFViewerRotateController();
        this.pdfViewerDrawController = new PDFViewerDrawController(this);
        this.pdfViewerPointerController = new PDFViewerPointerController(this);
        this.pdfViewerNavigationController = new PDFViewerNavigationController(this,0);
        this.pdfViewerFileController = new PDFViewerFileController(this);
        this.pdfViewerToolController = new PDFViewerToolController(this);
        this.stage = stage;
        this.mainWindow = mainWindow;
        setupLayout(prefWidth,prefHeight);

        // If esc is pressed then exit fullscreen with our function
        stage.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                pdfViewerToolController.setFullScreen(false);
                event.consume();
            }
        });
    }

    public PDFViewer(PDFViewerObserver pdfViewerObserver ,Stage stage , MainWindow mainWindow , double prefWidth, double prefHeight) {
        this(stage,mainWindow,prefWidth,prefHeight);
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
            double zoomFactor = event.getZoomFactor();
            zoom(zoomFactor > 1 ? 2 : 0);
            event.consume();
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

        this.setCenter(viewArea);
        
        this.widthProperty().addListener((observable, oldValue, newValue) -> {
            updateSizeOfPdfPages((Double) newValue);
        });

        setViewAreaToControllers();
    }

    private void setViewAreaToControllers(){
        pdfViewerScrollController.setViewArea(viewArea);
        pdfViewerRotateController.setViewArea(viewArea);
        pdfViewerDrawController.setViewArea(viewArea);
    }

    public void updateSizeOfPdfPages(double newWidth) {
        double viewportHeight = viewArea.getViewportBounds().getHeight();

        if (pdfPages != null) {
            for (PDFPageContainer pdfPageContainer : pdfPages) {
                PDFPage pdfPage = pdfPageContainer.getPDFPage();
                pdfPage.setMinWidth(newWidth);
                pdfPage.setMinHeight(viewportHeight);
            }
        }
    }

    public void goPage(int index) {
        pdfViewerNavigationController.goPage(index);
        pdfViewerObserver.pageChanged(index);
    }

    public void goBackPage() {
        pdfViewerNavigationController.goBackPage();
        pdfViewerObserver.pageChangedGoBack();
    }

    public void goNextPage() {
        pdfViewerNavigationController.goNextPage();
        pdfViewerObserver.pageChangedGoNext();
    }

    public void zoom(double zoomFactor) {
        pdfViewerZoomController.zoom(zoomFactor);
        pdfViewerObserver.zoomed(zoomFactor);
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
    }

    public void draw(MouseCoordinate[] mouseCoordinates, Color color, double size) {
        pdfViewerDrawController.draw(mouseCoordinates,color,size);
    }

    public void erase(MouseCoordinate[] mouseCoordinates, double size) {
        pdfViewerDrawController.erase(mouseCoordinates,size);
    }

    public void point(double x , double y) {
        pdfViewerPointerController.point(x ,y);
    }

    public void loadPDF(String path) {
        pdfViewerFileController.loadPDF(path , 1 , true);
    }

    public void loadPDF(String path , int currentIndex) throws IOException {
        pdfViewerFileController.loadPDF(path , currentIndex , false);
    }

    public void loadPDF(BufferedImage[] bufferedImages , String fileName) {
        pdfViewerFileController.loadPDF(bufferedImages , fileName);
    }

    public void showFullScreen(boolean isShowFullScreen) {

        stage.setFullScreen(isShowFullScreen);
        if (isShowFullScreen) {
            mainWindow.topSide.setPrefHeight(0);
            mainWindow.topSide.setMinHeight(0);
            mainWindow.topSide.setMaxHeight(0);
            mainWindow.pdfViewer.setPrefHeight(mainWindow.sceneHeight * 1.052);
        }
        else {
            mainWindow.topSide.setPrefHeight(mainWindow.sceneHeight * 0.065);
            mainWindow.topSide.setMinHeight(mainWindow.sceneHeight * 0.065);
            mainWindow.topSide.setMaxHeight(mainWindow.sceneHeight * 0.065);
            mainWindow.pdfViewer.setPrefHeight(mainWindow.sceneHeight * 0.935);
        }
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

    public PDFViewerZoomController getPdfViewerZoomController() {
        return pdfViewerZoomController;
    }
    public PDFViewerRotateController getPdfViewerRotateController() {
        return pdfViewerRotateController;
    }
    public PDFViewerToolController getPdfViewerToolController() {
        return pdfViewerToolController;
    }

    public PDFViewerPointerController getPdfViewerPointerController() {
        return pdfViewerPointerController;
    }


}
