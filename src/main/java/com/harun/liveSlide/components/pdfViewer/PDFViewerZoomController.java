package com.harun.liveSlide.components.pdfViewer;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;

public class PDFViewerZoomController {
    private PDFViewer pdfViewer;
    private ScrollPane viewArea;
    private double totalZoom = 1;
    final private double ZOOM_SPEED = 1.020;
    final private double NEGATIVE_ZOOM_SPEED = 2 - ZOOM_SPEED;
    int currentZoomRate = 100;

    public PDFViewerZoomController(PDFViewer pdfViewer){
        this.pdfViewer = pdfViewer;
    }

    public void zoom(double zoomFactor) {
        double zoomRate = getStaticZoomRate(zoomFactor);

        // If Zoom is in limited rate
        if (isZoomable(totalZoom,zoomRate)) {
            totalZoom *= zoomRate;

            Group group = (Group) viewArea.getContent();
            PDFPage pdfPage = (PDFPage) group.getChildren().get(0);

            pdfPage.image.setScaleX(pdfPage.image.getScaleX() * zoomRate);
            pdfPage.image.setScaleY(pdfPage.image.getScaleY() * zoomRate);
            pdfPage.canvas.setScaleX(pdfPage.canvas.getScaleX() * zoomRate);
            pdfPage.canvas.setScaleY(pdfPage.canvas.getScaleY() * zoomRate);

            if (zoomRate > 1) {
                currentZoomRate += 5;
            }
            else{
                currentZoomRate -= 5;
            }

            pdfViewer.toolBar.updateZoomRateLabelText(currentZoomRate);
        }
    }

    private double getStaticZoomRate(double zoomFactor) {
        if (zoomFactor > 1) {
            return ZOOM_SPEED;
        }
        else {
            return NEGATIVE_ZOOM_SPEED;
        }
    }

    private boolean isZoomable (double totalZoom , double zoomRate) {
        return (totalZoom > 0.7 || zoomRate > 1) && ((totalZoom < 3) || zoomRate < 1);
    }

    public void setViewArea(ScrollPane viewArea) {
        this.viewArea = viewArea;
    }

    public void zoomOut(ActionEvent actionEvent) {
        zoom(0);
    }

    public void zoomIn(ActionEvent actionEvent) {
        zoom(2);
    }

    /* For scrollable multi-view */
    /*
    private void zoomEachPage(VBox vbox , double zoomRate) {

        for (Node node : vbox.getChildren()) {
            PDFPage pdfPage = (PDFPage) node;

            pdfPage.image.setScaleX(pdfPage.image.getScaleX() * zoomRate);
            pdfPage.image.setScaleY(pdfPage.image.getScaleY() * zoomRate);
            pdfPage.canvas.setScaleX(pdfPage.canvas.getScaleX() * zoomRate);
            pdfPage.canvas.setScaleY(pdfPage.canvas.getScaleY() * zoomRate);

        }
    }

    private void preventScrollDueZoom(ScrollPane scrollableArea , double zoomRate) {
        // Zoom In
        if (zoomRate > 1) {
            scrollableArea.setVvalue(scrollableArea.getVvalue() * ZOOM_SPEED);
        }
        // Zoom out
        else {
            scrollableArea.setVvalue(scrollableArea.getVvalue() / ZOOM_SPEED);
        }
    }*/
}
