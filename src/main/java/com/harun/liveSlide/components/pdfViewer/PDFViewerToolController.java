package com.harun.liveSlide.components.pdfViewer;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.Objects;

public class PDFViewerToolController {
    private final PDFViewer pdfViewer;
    private PDFTool currentPdfTool = PDFTool.CURSOR;
    private int currentDrawSize = 0;
    private int currentEraserSize = 0;
    private String currentDrawColor = "red";
    private boolean isFullScreen = false;

    public PDFViewerToolController(PDFViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
    }

    public PDFTool getCurrentPdfTool() {
        return currentPdfTool;
    }

    public void setCurrentPdfTool(PDFTool currentPdfTool) {
        this.currentPdfTool = currentPdfTool;
        if (pdfViewer.viewArea.getContent() != null) {
            PDFPageContainer pdfPageContainer = (PDFPageContainer) pdfViewer.viewArea.getContent();
            PDFPage pdfPage = pdfPageContainer.getPDFPage();

            if (currentPdfTool == PDFTool.CURSOR) {
                pdfViewer.viewArea.setPannable(true);
                pdfPage.canvas.setMouseTransparent(true);
                pdfViewer.toolBar.setCursorSelected();
            }
            else if(currentPdfTool == PDFTool.POINTER){
                pdfViewer.viewArea.setPannable(false);
                pdfPage.canvas.setMouseTransparent(false);
                pdfViewer.toolBar.setPointerSelected();
            }
            else if(currentPdfTool == PDFTool.DRAW){
                pdfViewer.viewArea.setPannable(false);
                pdfPage.canvas.setMouseTransparent(false);
                pdfViewer.toolBar.setDrawSelected();
            }
            else if(currentPdfTool == PDFTool.ERASER){
                pdfViewer.viewArea.setPannable(false);
                pdfPage.canvas.setMouseTransparent(false);
                pdfViewer.toolBar.setEraserSelected();
            }
        }
    }

    public void setCurrentDrawSize(int size) {
        if (currentDrawSize != size) {
            currentDrawSize = size;

            switch (currentDrawSize) {
                case 1 :
                    pdfViewer.toolBar.drawSmallSizeSelected();
                    break;
                case 2 :
                    pdfViewer.toolBar.drawMediumSizeSelected();
                    break;
                case 3:
                    pdfViewer.toolBar.drawBigSizeSelected();
                    break;
            }

            pdfViewer.getPdfViewerDrawController().setPenSize(size);
        }
    }

    public void setCurrentEraserSize(int size) {
        if (currentEraserSize != size) {
            currentEraserSize = size;

            switch (currentEraserSize) {
                case 1 :
                    pdfViewer.toolBar.eraserSmallSizeSelected();
                    break;
                case 2 :
                    pdfViewer.toolBar.eraserMediumSizeSelected();
                    break;
                case 3:
                    pdfViewer.toolBar.eraserBigSizeSelected();
                    break;
            }

            pdfViewer.getPdfViewerDrawController().setEraserSize(size * 30);
        }
    }

    public void setCurrentDrawColor(String color) {
        if (!currentDrawColor.equals(color)) {
            currentDrawColor = color;

            if ("black".equals(currentDrawColor)) {
                pdfViewer.toolBar.drawBlackSelected();
                pdfViewer.getPdfViewerDrawController().setPenColor(Color.BLACK);
            }
            else if ("red".equals(currentDrawColor)) {
                pdfViewer.toolBar.drawRedSelected();
                pdfViewer.getPdfViewerDrawController().setPenColor(Color.RED);
            }
            else if ("blue".equals(currentDrawColor)) {
                pdfViewer.toolBar.drawBlueSelected();
                pdfViewer.getPdfViewerDrawController().setPenColor(Color.BLUE);
            }
        }
    }

    public void cursor(ActionEvent actionEvent) {
        if (currentPdfTool != PDFTool.CURSOR)
            setCurrentPdfTool(PDFTool.CURSOR);
    }

    public void draw(ActionEvent actionEvent) {
        if (currentPdfTool != PDFTool.DRAW)
            setCurrentPdfTool(PDFTool.DRAW);
    }

    public void eraser(ActionEvent actionEvent) {
        if (currentPdfTool != PDFTool.ERASER)
            setCurrentPdfTool(PDFTool.ERASER);
    }

    public void pointer(ActionEvent actionEvent) {
        if (currentPdfTool != PDFTool.POINTER)
            setCurrentPdfTool(PDFTool.POINTER);
    }

    public void setColorBlack(ActionEvent actionEvent) {
        setCurrentDrawColor("black");
    }

    public void setColorRed(ActionEvent actionEvent) {
        setCurrentDrawColor("red");
    }

    public void setColorBlue(ActionEvent actionEvent) {
        setCurrentDrawColor("blue");
    }

    public void setDrawSizeSmall(ActionEvent actionEvent) {
        setCurrentDrawSize(1);
    }

    public void setDrawSizeMedium(ActionEvent actionEvent) {
        setCurrentDrawSize(2);
    }

    public void setDrawSizeBig(ActionEvent actionEvent) {
        setCurrentDrawSize(3);
    }

    public void setEraserSizeSmall(ActionEvent actionEvent) {
        setCurrentEraserSize(1);
    }

    public void setEraserSizeMedium(ActionEvent actionEvent) {
        setCurrentEraserSize(2);
    }

    public void setEraserSizeBig(ActionEvent actionEvent) {
        setCurrentEraserSize(3);
    }

    public void showFullScreen(ActionEvent actionEvent) {
        setFullScreen(!isFullScreen);
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        pdfViewer.showFullScreen(isFullScreen);
    }


}
