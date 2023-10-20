package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.model.MouseCoordinate;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class PDFViewer extends BorderPane {
    private final PDFViewerZoomController pdfViewerZoomController;
    private final PDFViewerScrollController pdfViewerScrollController;
    private final PDFViewerRotateController pdfViewerRotateController;
    private final PDFViewerDrawController pdfViewerDrawController;
    private final PDFViewerNavigationController pdfViewerNavigationController;
    private final PDFViewerFileController pdfViewerFileController;
    public ScrollPane viewArea;
    public PDFViewerToolBar toolBar;
    public ArrayList<Group> pdfPages;
    public String currentFilePath;

    public PDFViewer(double prefWidth, double prefHeight) {
        this.setId("pdf-viewer");
        this.pdfViewerZoomController = new PDFViewerZoomController();
        this.pdfViewerScrollController = new PDFViewerScrollController();
        this.pdfViewerRotateController = new PDFViewerRotateController();
        this.pdfViewerDrawController = new PDFViewerDrawController();
        this.pdfViewerNavigationController = new PDFViewerNavigationController(this,0);
        this.pdfViewerFileController = new PDFViewerFileController(this);
        setupLayout(prefWidth,prefHeight);
    }

    private void setupLayout(double prefWidth , double prefHeight){
        setPrefSize(prefWidth, prefHeight);

        // Tool Bar
        toolBar = new PDFViewerToolBar(30,pdfViewerNavigationController,pdfViewerFileController);
        toolBar.setId("pdf-viewer-toolbar");
        this.setTop(toolBar);

        // View Area
        viewArea = new ScrollPane();
        viewArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        viewArea.setOnZoom((ZoomEvent event) -> {
            double zoomFactor = event.getZoomFactor();
            pdfViewerZoomController.zoom(zoomFactor);
            event.consume();
        });

        this.setCenter(viewArea);
        this.widthProperty().addListener((observable, oldValue, newValue) -> {
            //TODO control if content is null
            double viewportWidth = viewArea.getViewportBounds().getWidth();
            double viewportHeight = viewArea.getViewportBounds().getHeight();

            if (pdfPages != null) {
                for (Group group : pdfPages) {
                    PDFPage pdfPage = (PDFPage) group.getChildren().get(0);
                    pdfPage.setMinWidth(viewportWidth);
                    pdfPage.setMinHeight(viewportHeight);
                }
            }
        });

        setViewAreaToControllers();
    }

    private void setViewAreaToControllers(){
        pdfViewerZoomController.setViewArea(viewArea);
        pdfViewerScrollController.setViewArea(viewArea);
        pdfViewerRotateController.setViewArea(viewArea);
        pdfViewerDrawController.setViewArea(viewArea);
    }

    public void goPage(int index) {
        pdfViewerNavigationController.goPage(index);
    }

    public void goBackPage() {
        pdfViewerNavigationController.goBackPage();
    }

    public void goNextPage() {
        pdfViewerNavigationController.goNextPage();
    }

    public void zoom(double zoomFactor) {
        pdfViewerZoomController.zoom(zoomFactor);
    }

    public void scrollVerticallyTo(double vValue) {
        pdfViewerScrollController.scrollVerticallyTo(vValue);
    }

    public void scrollHorizontallyTo(double hValue) {
        pdfViewerScrollController.scrollHorizontallyTo(hValue);
    }

    public void rotate() {
        pdfViewerRotateController.rotate();
    }

    public void draw(MouseCoordinate[] mouseCoordinates, Color color, double size) {
        pdfViewerDrawController.draw(mouseCoordinates,color,size);
    }

    public void loadPDF(String path) throws IOException {
        loadPDF(path,1);
    }

    public void loadPDF(String path , int currentIndex) throws IOException {
        pdfViewerFileController.loadPDF(path , currentIndex);
    }

    public void loadPDF(BufferedImage[] bufferedImages , String fileName) throws IOException {
        pdfViewerFileController.loadPDF(bufferedImages , fileName);
    }

    public void reloadGraphicsContext(){
        pdfViewerDrawController.reloadGraphicsContext();
    }

    public PDFViewerDrawController getPdfViewerDrawController() {
        return pdfViewerDrawController;
    }

    public PDFViewerNavigationController getPdfViewerNavigationController() {
        return pdfViewerNavigationController;
    }
}
