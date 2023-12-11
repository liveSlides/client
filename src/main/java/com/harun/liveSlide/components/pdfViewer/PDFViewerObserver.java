package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.NetworkManager;
import com.harun.liveSlide.model.MouseCoordinate;

public class PDFViewerObserver {
    private NetworkManager networkManager;

    public PDFViewerObserver() {

    }

    public PDFViewerObserver(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public void pageChanged(int index) {
        System.out.println(index + "ile sayfa değişti");
    }

    public void pageChangedGoBack() {
        System.out.println("Bir önceki sayfaya gidildi");
    }

    public void pageChangedGoNext() {
        System.out.println("Bir sonraki sayfaya gidildi");
    }

    public void zoomed(double zoomFactor) {
        System.out.println("Zoom : " + zoomFactor);
    }

    public void scrolledVerticallyTo(double vValue) {
        System.out.println("Scrolled Vertically To : " + vValue);
    }

    public void scrolledHorizontallyTo(double hValue) {
        System.out.println("Scrolled horizontally To : " + hValue);
    }

    public void rotated() {
        System.out.println("Rotated");
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
    }
}
