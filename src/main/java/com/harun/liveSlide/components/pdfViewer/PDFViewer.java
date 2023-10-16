package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.model.MouseCoordinate;
import com.harun.liveSlide.utils.BFImageConverter;
import com.harun.liveSlide.utils.DPICalculator;
import com.harun.liveSlide.utils.FileNameExtractor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PDFViewer extends BorderPane {
    private PDFViewerZoomController pdfViewerZoomController;
    private PDFViewerScrollController pdfViewerScrollController;
    private PDFViewerRotateController pdfViewerRotateController;
    private PDFViewerDrawController pdfViewerDrawController;
    private PDFViewerNavigationController pdfViewerNavigationController;
    public ScrollPane viewArea;
    public PDFViewerToolBar toolBar;
    public ArrayList<Group> pdfPages;


    public PDFViewer(double prefWidth, double prefHeight) {
        this.setId("pdf-viewer");
        this.pdfViewerZoomController = new PDFViewerZoomController();
        this.pdfViewerScrollController = new PDFViewerScrollController();
        this.pdfViewerRotateController = new PDFViewerRotateController();
        this.pdfViewerDrawController = new PDFViewerDrawController();
        this.pdfViewerNavigationController = new PDFViewerNavigationController(this,0);
        setupLayout(prefWidth,prefHeight);
    }

    private void setupLayout(double prefWidth , double prefHeight){
        setPrefSize(prefWidth, prefHeight);

        // Tool Bar
        toolBar = new PDFViewerToolBar(30,pdfViewerNavigationController);
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

        setViewAreaToControllers();
    }

    private void setViewAreaToControllers(){
        pdfViewerZoomController.setViewArea(viewArea);
        pdfViewerScrollController.setViewArea(viewArea);
        pdfViewerRotateController.setViewArea(viewArea);
        pdfViewerDrawController.setViewArea(viewArea);
    }

    public void loadPDF(BufferedImage[] bufferedImages , String fileName) {

        if (pdfPages != null) {
            pdfPages.clear();
        }
        else {
            pdfPages = new ArrayList<Group>();
        }

        for (int page = 0; page < bufferedImages.length; ++page) {
            PDFPage pdfPage = new PDFPage(BFImageConverter.imageToImageView(viewArea.getViewportBounds().getWidth(),bufferedImages[page]),pdfViewerDrawController);
            pdfPage.setMinWidth(viewArea.getViewportBounds().getWidth());
            Group group = new Group(pdfPage);
            pdfPages.add(group);
        }

        toolBar.setPdfTitleText(FileNameExtractor.getFileNameFromPath(fileName));
        pdfViewerNavigationController.setPageCount(bufferedImages.length);
        goPage(1);
    }

    public void loadPDF(String path) throws IOException {
        PDDocument document = PDDocument.load(new File(path));
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        int numberOfPages = document.getNumberOfPages();
        int dpi = DPICalculator.calculateDPI(numberOfPages);

        if (pdfPages != null) {
            pdfPages.clear();
        }
        else {
            pdfPages = new ArrayList<Group>();
        }

        for (int page = 0; page < numberOfPages; ++page) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, dpi, ImageType.RGB);
            PDFPage pdfPage = new PDFPage(BFImageConverter.imageToImageView(viewArea.getViewportBounds().getWidth(),bim) ,pdfViewerDrawController);
            pdfPage.setMinWidth(viewArea.getViewportBounds().getWidth());
            Group group = new Group(pdfPage);
            pdfPages.add(group);
        }

        toolBar.setPdfTitleText(FileNameExtractor.getFileNameFromPath(path));
        pdfViewerNavigationController.setPageCount(numberOfPages);
        goPage(1);

        document.close();
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

    public void reloadGraphicsContext(){
        pdfViewerDrawController.reloadGraphicsContext();
    }
}
