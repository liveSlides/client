package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.utils.BFImageConverter;
import com.harun.liveSlide.utils.FileNameExtractor;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PDFViewerFileController {
    PDFViewer pdfViewer;
    private PDDocument openDocument = null;
    private Canvas[] canvases;

    public PDFViewerFileController(PDFViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
    }

    public void loadPDF(BufferedImage[] bufferedImages , String fileName) {

        if (pdfViewer.pdfPages != null) {
            pdfViewer.pdfPages.clear();
        }
        else {
            pdfViewer.pdfPages = new ArrayList<>();
        }

        for (int page = 0; page < bufferedImages.length; ++page) {
            //PDFPage pdfPage = new PDFPage(BFImageConverter.imageToImageView(pdfViewer.viewArea.getViewportBounds().getWidth(),bufferedImages[page]),pdfViewer.getPdfViewerDrawController());
            //pdfPage.setMinWidth(pdfViewer.viewArea.getViewportBounds().getWidth());
            //Group group = new Group(pdfPage);
            //pdfViewer.pdfPages.add(group);
        }

        pdfViewer.toolBar.setPdfTitleText(FileNameExtractor.getFileNameFromPath(fileName));
        pdfViewer.getPdfViewerNavigationController().setPageCount(bufferedImages.length);
        pdfViewer.goPage(1);
    }

    public void loadPDF(String path, int currentIndex , boolean initialization) {
        try {

            int numberOfPages = 0;
            int range = 10;

            if (initialization && openDocument != null) {
                openDocument.close();
            }
            if (initialization) {
                openDocument = PDDocument.load(new File(path));
                numberOfPages = openDocument.getNumberOfPages();
                canvases = new Canvas[numberOfPages];
                for (int i = 0; i < numberOfPages; i++) {
                    canvases[i] = new Canvas();
                }
            }
            else {
                numberOfPages = openDocument.getNumberOfPages();
            }
            
            PDFRenderer pdfRenderer = new PDFRenderer(openDocument);

            clearPdfPages();

            int startIndex = Math.max(1, currentIndex - range);
            int endIndex = Math.min(numberOfPages, currentIndex + range);

            loadPDF(pdfRenderer, numberOfPages, startIndex, endIndex);

            pdfViewer.toolBar.setPdfTitleText(FileNameExtractor.getFileNameFromPath(path));
            pdfViewer.getPdfViewerNavigationController().setPageCount(numberOfPages);
            pdfViewer.getPdfViewerNavigationController().setPageStartIndex(startIndex);
            pdfViewer.getPdfViewerNavigationController().setPageEndIndex(endIndex);
            pdfViewer.goPage(currentIndex);
            pdfViewer.currentFilePath = path;
        } catch (IOException e) {
            System.out.println("ERROR: File can not be opened!");
        }
    }

    private void loadPDF(PDFRenderer pdfRenderer , int numberOfPages , int startIndex , int endIndex) throws IOException {
        for (int page = 0; page < numberOfPages; ++page) {
            if (page + 1 >= startIndex && page + 1 <= endIndex){
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 150, ImageType.RGB);
                PDFPage pdfPage = new PDFPage(BFImageConverter.imageToImageView(pdfViewer.viewArea.getViewportBounds().getWidth(),bim) ,canvases[page],pdfViewer.getPdfViewerDrawController());
                pdfPage.setMinWidth(pdfViewer.viewArea.getViewportBounds().getWidth());
                pdfPage.setMinHeight(pdfViewer.viewArea.getViewportBounds().getWidth());
                Group group = new Group(pdfPage);
                pdfViewer.pdfPages.add(group);
            }
        }
    }

    public void openFileSelection(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Slide File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if (openDocument != null){
            try {
                openDocument.close();
            }catch (Exception e){
                System.out.println("Dosya kapatılamadı!");
            }
        }
        loadPDF(file.getAbsolutePath() , 1 , true);

        //PDF Viewer initializing when file is uploaded
        pdfViewer.viewArea.setVvalue(0.5);
        pdfViewer.getPdfViewerToolController().setCurrentPdfTool(PDFTool.POINTER);
        pdfViewer.getPdfViewerToolController().setCurrentDrawColor("black");
        pdfViewer.getPdfViewerToolController().setCurrentDrawSize(1);
        pdfViewer.getPdfViewerToolController().setCurrentEraserSize(1);
    }

    private void clearPdfPages() {
        if (pdfViewer.pdfPages != null) {
            pdfViewer.pdfPages.clear();
        } else {
            pdfViewer.pdfPages = new ArrayList<>();
        }
    }
}
