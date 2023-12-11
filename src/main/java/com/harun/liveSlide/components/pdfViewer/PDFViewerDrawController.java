package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.model.MouseCoordinate;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class PDFViewerDrawController {
    private PDFViewer pdfViewer;
    private ScrollPane viewArea;
    private GraphicsContext gc;
    private Paint prefferedPenColor = Color.BLACK;
    private double prefferedPenSize = 1;
    private double prefferedEraserSize = 1;

    public PDFViewerDrawController(PDFViewer pdfViewer){
        this.pdfViewer = pdfViewer;
    }

    public void onMousePressed(double x , double y) {
        if (pdfViewer.getPdfViewerToolController().getCurrentPdfTool() == PDFTool.DRAW) {
            gc.setStroke(prefferedPenColor);
            gc.setLineWidth(prefferedPenSize);
            gc.beginPath();
            gc.moveTo(x, y);
        }
        else if(pdfViewer.getPdfViewerToolController().getCurrentPdfTool() == PDFTool.ERASER) {
            gc.clearRect(x,y,prefferedEraserSize,prefferedEraserSize);
        }
    }

    public void onMouseDragged(double x , double y) {
        if (pdfViewer.getPdfViewerToolController().getCurrentPdfTool() == PDFTool.DRAW) {
            gc.lineTo(x, y);
            gc.stroke();
        }
        else if(pdfViewer.getPdfViewerToolController().getCurrentPdfTool() == PDFTool.ERASER) {
            gc.clearRect(x,y,prefferedEraserSize,prefferedEraserSize);
        }
    }

    public void reloadGraphicsContext() {
        if (viewArea != null && viewArea.getContent() instanceof PDFPageContainer) {
            PDFPageContainer pdfPageContainer = (PDFPageContainer) viewArea.getContent();
            PDFPage pdfPage = pdfPageContainer.getPDFPage();

            if (pdfPage != null && pdfPage.canvas != null) {
                this.gc = pdfPage.canvas.getGraphicsContext2D();
            }
        }
    }

    public void setViewArea(ScrollPane viewArea) {
        this.viewArea = viewArea;
    }

    public void setPenColor(Paint color) {
        prefferedPenColor = color;
    }

    public void setPenSize(double size) {
        prefferedPenSize  = size;
    }

    public void setEraserSize(double prefferedEraserSize) {
        this.prefferedEraserSize = prefferedEraserSize;
    }
}
