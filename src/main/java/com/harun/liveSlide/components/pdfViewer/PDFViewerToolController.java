package com.harun.liveSlide.components.pdfViewer;

import javafx.event.ActionEvent;
import javafx.scene.Group;

public class PDFViewerToolController {
    private PDFViewer pdfViewer;
    private PDFTool currentPdfTool = PDFTool.POINTER;
    private int currentDrawSize = 1;
    private int currentEraserSize = 1;
    private String currentDrawColor = "Black";

    public PDFViewerToolController(PDFViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
    }

    public PDFTool getCurrentPdfTool() {
        return currentPdfTool;
    }

    public void setCurrentPdfTool(PDFTool currentPdfTool) {
        this.currentPdfTool = currentPdfTool;
        if (pdfViewer.viewArea.getContent() != null) {
            Group group = (Group) pdfViewer.viewArea.getContent();
            PDFPage pdfPage = (PDFPage) group.getChildren().get(0);

            if (currentPdfTool == PDFTool.POINTER) {
                pdfViewer.viewArea.setPannable(true);
                pdfPage.canvas.setMouseTransparent(true);
                pdfViewer.toolBar.setPointerSelected();
            }
            else if(currentPdfTool == PDFTool.DRAW){
                pdfViewer.viewArea.setPannable(false);
                pdfPage.canvas.setMouseTransparent(false);
                pdfViewer.toolBar.setDrawSelected();
            }
            else if(currentPdfTool == PDFTool.ERASER){
                pdfViewer.viewArea.setPannable(false);
                pdfViewer.toolBar.setEraserSelected();
            }
        }
    }

    public void pointer(ActionEvent actionEvent) {
        if (currentPdfTool != PDFTool.POINTER)
            setCurrentPdfTool(PDFTool.POINTER);
    }

    public void draw(ActionEvent actionEvent) {
        if (currentPdfTool != PDFTool.DRAW)
            setCurrentPdfTool(PDFTool.DRAW);
    }

    public void eraser(ActionEvent actionEvent) {
        if (currentPdfTool != PDFTool.ERASER)
            setCurrentPdfTool(PDFTool.ERASER);
    }

    public void setColorBlack(ActionEvent actionEvent) {
    }

    public void setColorRed(ActionEvent actionEvent) {
    }

    public void setDrawSizeSmall(ActionEvent actionEvent) {
    }

    public void setDrawSizeMedium(ActionEvent actionEvent) {
    }

    public void setDrawSizeBig(ActionEvent actionEvent) {
    }

    public void setEraserSizeSmall(ActionEvent actionEvent) {
    }

    public void setEraserSizeMedium(ActionEvent actionEvent) {
    }

    public void setEraserSizeBig(ActionEvent actionEvent) {
    }
}
