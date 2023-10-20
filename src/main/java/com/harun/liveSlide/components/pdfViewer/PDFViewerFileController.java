package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.utils.BFImageConverter;
import com.harun.liveSlide.utils.DPICalculator;
import com.harun.liveSlide.utils.FileNameExtractor;
import javafx.event.ActionEvent;
import javafx.scene.Group;
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

    public PDFViewerFileController(PDFViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
    }

    public void loadPDF(BufferedImage[] bufferedImages , String fileName) {

        if (pdfViewer.pdfPages != null) {
            pdfViewer.pdfPages.clear();
        }
        else {
            pdfViewer.pdfPages = new ArrayList<Group>();
        }

        for (int page = 0; page < bufferedImages.length; ++page) {
            PDFPage pdfPage = new PDFPage(BFImageConverter.imageToImageView(pdfViewer.viewArea.getViewportBounds().getWidth(),bufferedImages[page]),pdfViewer.getPdfViewerDrawController());
            pdfPage.setMinWidth(pdfViewer.viewArea.getViewportBounds().getWidth());
            Group group = new Group(pdfPage);
            pdfViewer.pdfPages.add(group);
        }

        pdfViewer.toolBar.setPdfTitleText(FileNameExtractor.getFileNameFromPath(fileName));
        pdfViewer.getPdfViewerNavigationController().setPageCount(bufferedImages.length);
        pdfViewer.goPage(1);
    }

    public void loadPDF(String path , int currentIndex) throws IOException {
        PDDocument document = PDDocument.load(new File(path));
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        int numberOfPages = document.getNumberOfPages();
        int dpi = DPICalculator.calculateDPI(numberOfPages);

        if (pdfViewer.pdfPages != null) {
            pdfViewer.pdfPages.clear();
        }
        else {
            pdfViewer.pdfPages = new ArrayList<Group>();
        }

        int startIndex = 1;
        if (currentIndex - 10 >= 1) {
            startIndex = currentIndex - 10;
        }

        int endIndex = numberOfPages;
        if (currentIndex + 10 < numberOfPages ){
            endIndex = currentIndex + 10;
        }

        loadPDF(pdfRenderer,numberOfPages,dpi,startIndex,endIndex);

        pdfViewer.toolBar.setPdfTitleText(FileNameExtractor.getFileNameFromPath(path));
        pdfViewer.getPdfViewerNavigationController().setPageCount(numberOfPages);
        pdfViewer.getPdfViewerNavigationController().setPageStartIndex(startIndex);
        pdfViewer.getPdfViewerNavigationController().setPageEndIndex(endIndex);
        pdfViewer.goPage(currentIndex);
        pdfViewer.currentFilePath = path;

        document.close();
    }

    private void loadPDF(PDFRenderer pdfRenderer , int numberOfPages , int dpi , int startIndex , int endIndex) throws IOException {
        for (int page = 0; page < numberOfPages; ++page) {
            if (page + 1 >= startIndex && page + 1 <= endIndex){
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, dpi, ImageType.RGB);
                PDFPage pdfPage = new PDFPage(BFImageConverter.imageToImageView(pdfViewer.viewArea.getViewportBounds().getWidth(),bim) , pdfViewer.getPdfViewerDrawController());
                pdfPage.setMinWidth(pdfViewer.viewArea.getViewportBounds().getWidth());
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
        try{
            loadPDF(file.getAbsolutePath() , 1);
        }
        catch (IOException e) {
            System.out.println("ERROR : File can not be opened!");
        }
    }
}
