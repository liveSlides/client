package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.utils.BFImageConverter;
import com.harun.liveSlide.utils.FileNameExtractor;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.stage.Screen;

public class PDFViewerFileController {
    PDFViewer pdfViewer;
    private PDDocument openDocument = null;
    private Canvas[] canvases;
    private double screenWidth = -1;

    public PDFViewerFileController(PDFViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
    }

    public void loadPDF(String path, int currentIndex , boolean initialization , double screenWidth) {
        this.screenWidth = screenWidth;
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
                //Render pdf
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 150, ImageType.RGB);

                //Create PDF Page
                Screen screen = Screen.getPrimary();
                double viewportBoundsWidth = screenWidth == -1 ? screen.getBounds().getWidth() : screenWidth;

                PDFPage pdfPage = new PDFPage(pdfViewer,BFImageConverter.imageToImageView(viewportBoundsWidth,bim) ,canvases[page],pdfViewer.getPdfViewerDrawController(),pdfViewer.getPdfViewerPointerController() , bim);
                pdfPage.setMinWidth(viewportBoundsWidth);
                pdfPage.setMinHeight(viewportBoundsWidth);

                //Encapsulate PDF Page with PdfPageContainer
                PDFPageContainer pdfPageContainer = new PDFPageContainer(pdfPage);
                pdfPageContainer.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {pdfViewer.pointByPresenter(event.getX(),event.getY());});
                pdfPageContainer.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {pdfViewer.pointByPresenter(event.getX(),event.getY());});

                pdfViewer.pdfPages.add(pdfPageContainer);
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

        if (GlobalVariables.userType != UserType.HOST_PRESENTER)
            pdfViewer.loadPDF(file.getAbsolutePath());
        else
            pdfViewer.notifyLoadPdf(file.getAbsolutePath());

        //PDF Viewer initializing when file is uploaded
        pdfViewer.viewArea.setVvalue(0.5);
        pdfViewer.changeActiveTool(PDFTool.CURSOR);
        pdfViewer.changeActivePenColor(PenColor.BLACK);
        pdfViewer.changeActivePenSize(PenEraserSize.SMALL);
        pdfViewer.changeActiveEraserSize(PenEraserSize.SMALL);
    }

    private void clearPdfPages() {
        if (pdfViewer.pdfPages != null) {
            pdfViewer.pdfPages.clear();
        } else {
            pdfViewer.pdfPages = new ArrayList<>();
        }
    }

    public Canvas[] getCanvases() {
        return canvases;
    }
}
