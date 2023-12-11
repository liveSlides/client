package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.NetworkManager;
import com.harun.liveSlide.model.MouseCoordinate;
import javafx.scene.paint.Color;


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

    public void drawed(MouseCoordinate[] mouseCoordinates, Color color, double size) {

    }

    public void erased(MouseCoordinate[] mouseCoordinates, double size) {

    }

    public void pointed(double x , double y) {

    }

    public void loadedPDF(String path) {
    }

}
