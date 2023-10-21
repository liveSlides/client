package com.harun.liveSlide.components.pdfViewer;

import javafx.event.ActionEvent;
import javafx.scene.Group;

public class PDFViewerToolController {
    private PDFViewer pdfViewer;
    private PDFTool currentPdfTool = PDFTool.POINTER;
    public PDFViewerToolController(PDFViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
    }

    public PDFTool getCurrentPdfTool() {
        return currentPdfTool;
    }

    public void setCurrentPdfTool(PDFTool currentPdfTool) {
        this.currentPdfTool = currentPdfTool;
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

    public void pointer(ActionEvent actionEvent) {
        setCurrentPdfTool(PDFTool.POINTER);
    }

    public void draw(ActionEvent actionEvent) {
        setCurrentPdfTool(PDFTool.DRAW);
    }

    public void eraser(ActionEvent actionEvent) {
        setCurrentPdfTool(PDFTool.ERASER);
    }
}
