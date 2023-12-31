package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.network.meeting.CanvasEventLog;
import com.harun.liveSlide.model.network.meeting.CanvasEventType;
import com.harun.liveSlide.screens.mainScreen.MainScreen;
import com.harun.liveSlide.model.MouseCoordinate;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.xml.stream.EventFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

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
    private final EventHandler<ZoomEvent> viewAreaZoomEventHandler = (ZoomEvent event) -> {
            double zoomFactor = event.getZoomFactor();
            zoom(zoomFactor > 1 ? 2 : 0);
            event.consume();
    };

    private EventHandler<ScrollEvent> scrollHandler = Event::consume;


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

        //TODO
        /*
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
        });*/

        viewArea.vvalueProperty().addListener((observable, oldValue, newValue) -> {pdfViewerObserver.scrolledVerticallyTo((Double) newValue);});
        viewArea.hvalueProperty().addListener((observable, oldValue, newValue) -> {pdfViewerObserver.scrolledHorizontallyTo((Double) newValue);});

        this.setCenter(viewArea);

        setViewAreaToControllers();
    }

    private void setViewAreaToControllers(){
        pdfViewerScrollController.setViewArea(viewArea);
        pdfViewerDrawController.setViewArea(viewArea);
    }

    public void goPage(int index) {
        pdfViewerNavigationController.goPage(index);
        pdfViewerObserver.pageChanged(
                index,
                pdfViewerZoomController.getCurrentPdfPage().getCurrentZoomRate(),
                viewArea.getVvalue(),
                viewArea.getHvalue());
    }

    public void goBackPage() {
        if (pdfViewerNavigationController.goBackPage()){
            int index = pdfViewerNavigationController.getCurrentPageIndex();
            pdfViewerObserver.pageChanged(
                    index,
                    pdfViewerZoomController.getCurrentPdfPage().getCurrentZoomRate(),
                    viewArea.getVvalue(),
                    viewArea.getHvalue());
        }
    }

    public void goNextPage() {
        if (pdfViewerNavigationController.goNextPage()) {
            int index = pdfViewerNavigationController.getCurrentPageIndex();
            pdfViewerObserver.pageChanged(
                    index,
                    pdfViewerZoomController.getCurrentPdfPage().getCurrentZoomRate(),
                    viewArea.getVvalue(),
                    viewArea.getHvalue());
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
        double hostScreenWidth = Screen.getPrimary().getBounds().getWidth();
        pdfViewerObserver.loadedPDF(path,hostScreenWidth);
    }

    public void loadPDF(String path) {
        pdfViewerFileController.loadPDF(path , 1 , true , -1);
    }

    public void loadPDF(String path, double screenWidth) {
        pdfViewerFileController.loadPDF(path , 1 , true , screenWidth);
    }

    public void loadPDF(String path , int index) throws IOException {
        pdfViewerFileController.loadPDF(path , index , false, -1);
    }

    public void setToolsVisible(boolean visibility) {
        toolBar.setToolsVisible(visibility);
    }

    public void setFileUploadToolVisible(boolean visibility) {
        toolBar.setFileUploadToolVisible(visibility);
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

    public PDFViewerObserver getPdfViewerObserver() {
        return pdfViewerObserver;
    }

    public void drawPages(LinkedList<CanvasEventLog>[] canvasEvents) {
        int index = 0;
        for (LinkedList i : canvasEvents) {
            Iterator iterator = i.iterator();

            while (iterator.hasNext()) {
                CanvasEventLog event = (CanvasEventLog) iterator.next();
                GraphicsContext gc = pdfViewerFileController.getCanvases()[index].getGraphicsContext2D();
                pdfViewerDrawController.drawPage(
                        event.getEventType() ,
                        event.getCanvasEvent().getX(),
                        event.getCanvasEvent().getY(),
                        event.getActiveTool(),
                        event.getPenSize(),
                        event.getEraserSize(),
                        event.getPenColor(),
                        gc );
            }

            index += 1;
        }
    }


    public void setActiveZoomingGesture(boolean isActive) {
        if (isActive)
            viewArea.setOnZoom(viewAreaZoomEventHandler);
        else
            viewArea.setOnZoom(null);
    }

    public void setActiveScrollingGesture(boolean isActive) {
        if (isActive)
            viewArea.removeEventFilter(ScrollEvent.SCROLL, scrollHandler);
        else
            viewArea.addEventFilter(ScrollEvent.SCROLL, scrollHandler);
    }
}
