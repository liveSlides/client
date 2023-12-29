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

    public void pageChanged(int index,int zoomRate,double vValue,double hValue) {
        networkMainManager.pageChanged(index, zoomRate, vValue, hValue);
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
        networkMainManager.canvasPressed(mouseCoordinate);
    }

    public void canvasDragged(MouseCoordinate mouseCoordinate) {
        networkMainManager.canvasDragged(mouseCoordinate);
    }

    public void activeToolChanged(PDFTool pdfTool) {
        networkMainManager.activeToolChanged(pdfTool);
    }

    public void penSizeChanged(PenEraserSize size) {
        networkMainManager.penSizeChanged(size);
    }

    public void penColorChanged(PenColor penColor) {
        networkMainManager.penColorChanged(penColor);
    }

    public void eraserSizeChanged(PenEraserSize size) {
        networkMainManager.eraserSizeChanged(size);
    }

    public void pointed(double x , double y) {
        networkMainManager.pointed(x,y);
    }

    public void loadedPDF(String path) {
        networkMainManager.uploadPDFToS3(path);
    }
}
