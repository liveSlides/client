package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.network.INetworkSenderManager;
import com.harun.liveSlide.model.MouseCoordinate;
import com.harun.liveSlide.network.NetworkMainManager;

public class PDFViewerObserver {
    private NetworkMainManager networkMainManager;

    public PDFViewerObserver() {
    }

    public PDFViewerObserver(NetworkMainManager networkMainManager) {
        this.networkMainManager = networkMainManager;
    }

    public void pageChanged(int index) {
        networkMainManager.pageChanged(index);
    }

    public void zoomed(int zoomRate) {
        networkMainManager.zoomed(zoomRate);
    }

    public void scrolledVerticallyTo(double vValue) {
        networkMainManager.scrolledVerticallyTo(vValue);
    }

    public void scrolledHorizontallyTo(double hValue) {
        networkMainManager.scrolledHorizontallyTo(hValue);
    }

    public void rotated(int rotateRate) {
        networkMainManager.rotated(rotateRate);
    }

    public void canvasPressed(MouseCoordinate mouseCoordinate) {
        System.out.println("Canvas Pressed x :" + mouseCoordinate.x + " y : " + mouseCoordinate.y);
    }

    public void canvasDragged(MouseCoordinate mouseCoordinate) {
        System.out.println("Canvas Dragged x :" + mouseCoordinate.x + " y : " + mouseCoordinate.y);
    }

    public void activeToolChanged(PDFTool pdfTool) {
        System.out.println("Active tool changed : " + pdfTool);
    }

    public void penSizeChanged(PenEraserSize size) {
        System.out.println("Pen size changed : " + size);
    }

    public void penColorChanged(PenColor penColor) {
        System.out.println("Pen color changed : " + penColor);
    }

    public void eraserSizeChanged(PenEraserSize size) {
        System.out.println("Eraser size changed : " + size);
    }

    public void pointed(double x , double y) {
        System.out.println("Pointer x : " + x + " y : " + y);
    }

    public void loadedPDF(String path) {
        networkMainManager.uploadPDFToS3(path);
    }
}
