package com.harun.liveSlide.components.pdfViewer;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;

public class PDFViewerZoomController {
    private PDFViewer pdfViewer;
    final private double ZOOM_SPEED = 1.020;
    final private double NEGATIVE_ZOOM_SPEED = 2 - ZOOM_SPEED;

    public PDFViewerZoomController(PDFViewer pdfViewer){
        this.pdfViewer = pdfViewer;
    }

    public void zoom(double zoomFactor) {
        double zoomRate = getStaticZoomRate(zoomFactor);

        PDFPage currentPDFPage = getCurrentPdfPage();
        if (currentPDFPage == null)
            return;

        Integer currentZoomRate = getCurrentPdfPage().getCurrentZoomRate();

        // If Zoom is in limited rate
        if (isZoomable(zoomRate)) {
            PDFPageContainer pdfPageContainer = (PDFPageContainer) pdfViewer.viewArea.getContent();
            if (pdfPageContainer != null) {
                PDFPage pdfPage = pdfPageContainer.getPDFPage();

                pdfPage.image.setScaleX(pdfPage.image.getScaleX() * zoomRate);
                pdfPage.image.setScaleY(pdfPage.image.getScaleY() * zoomRate);
                pdfPage.canvas.setScaleX(pdfPage.canvas.getScaleX() * zoomRate);
                pdfPage.canvas.setScaleY(pdfPage.canvas.getScaleY() * zoomRate);

                if (zoomRate > 1) {
                    currentPDFPage.setCurrentZoomRate(currentZoomRate + 5);
                }
                else{
                    currentPDFPage.setCurrentZoomRate(currentZoomRate - 5);
                }

                pdfViewer.toolBar.updateZoomRateLabelText(currentPDFPage.getCurrentZoomRate());
            }
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

    private boolean isZoomable (double zoomRate) {
        PDFPage currentPDFPage = getCurrentPdfPage();
        if (currentPDFPage == null)
            return false;

        Integer currentZoomRate = getCurrentPdfPage().getCurrentZoomRate();

        return (currentZoomRate > 10 || zoomRate > 1) && ((currentZoomRate < 300) || zoomRate < 1);
    }

    public void zoomToZoomRate(int zoomRate) {
        PDFPage currentPDFPage = getCurrentPdfPage();
        if (currentPDFPage == null)
            return;

        if (zoomRate == currentPDFPage.getCurrentZoomRate())
            return;

        while (zoomRate > currentPDFPage.getCurrentZoomRate()) {
            zoom(2);
        }

        while (zoomRate < currentPDFPage.getCurrentZoomRate()) {
            zoom(0);
        }
    }

    public PDFPage getCurrentPdfPage() {
        // Get current page zoom rate
        if (pdfViewer.viewArea.getContent() == null || !(pdfViewer.viewArea.getContent() instanceof PDFPageContainer))
            return null;

        return ((PDFPageContainer) pdfViewer.viewArea.getContent()).getPDFPage();
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
