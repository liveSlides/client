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

    }

    public void pageChangedGoBack() {

    }

    public void pageChangedGoNext() {

    }

    public void zoomed(double zoomFactor) {

    }

    public void scrolledVerticallyTo(double vValue) {

    }

    public void scrolledHorizontallyTo(double hValue) {

    }

    public void rotated() {

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
