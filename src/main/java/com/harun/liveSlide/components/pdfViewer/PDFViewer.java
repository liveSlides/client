package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.utils.BFImageConverter;
import com.harun.liveSlide.utils.DPICalculator;
import com.harun.liveSlide.utils.FileNameExtractor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFViewer extends BorderPane {
    public PDFViewerZoomController pdfViewerZoomController;
    public PDFViewerNavigationController pdfViewerNavigationController;
    public ScrollPane viewArea;
    public PDFViewerToolBar toolBar;
    private Group[] pdfPages;


    public PDFViewer(double prefWidth, double prefHeight) {
        this.setId("pdf-viewer");
        this.pdfViewerZoomController = new PDFViewerZoomController();
        this.pdfViewerNavigationController = new PDFViewerNavigationController(this,0);
        setupLayout(prefWidth,prefHeight);
    }

    private void setupLayout(double prefWidth , double prefHeight){
        setPrefSize(prefWidth, prefHeight);

        // Tool Bar
        toolBar = new PDFViewerToolBar(30,this);
        toolBar.setId("pdf-viewer-toolbar");
        this.setTop(toolBar);

        // View Area
        viewArea = new ScrollPane();
        viewArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        viewArea.setOnZoom((ZoomEvent event) -> {
            double zoomFactor = event.getZoomFactor();
            pdfViewerZoomController.zoom(zoomFactor,viewArea);
            event.consume();
        });
        this.setCenter(viewArea);
    }

    public void loadPDF(String path) throws IOException {
        PDDocument document = PDDocument.load(new File(path));
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        int numberOfPages = document.getNumberOfPages();
        int dpi = DPICalculator.calculateDPI(numberOfPages);

        pdfPages = new Group[numberOfPages];

        for (int page = 0; page < numberOfPages; ++page) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, dpi, ImageType.RGB);
            PDFPage pdfPage = new PDFPage(BFImageConverter.imageToImageView(viewArea.getViewportBounds().getWidth(),bim));
            pdfPage.setMinWidth(viewArea.getViewportBounds().getWidth());
            Group group = new Group(pdfPage);
            pdfPages[page] = group;
        }

        toolBar.setPdfTitleText(FileNameExtractor.getFileNameFromPath(path));
        pdfViewerNavigationController.setPageCount(numberOfPages);
        showPage(1);

        document.close();
    }

    public void showPage(int index) {
        viewArea.setContent(pdfPages[index - 1]);
        toolBar.updatePageIndicator(index , pdfPages.length);
    }



}
