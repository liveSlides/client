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
    private ArrayList<MouseCoordinate> drawingCoordinates;
    private ArrayList<MouseCoordinate> erasingCoordinates;
    private Paint prefferedPenColor = Color.BLACK;
    private double prefferedPenSize = 1;
    private double prefferedEraserSize = 1;

    public PDFViewerDrawController(PDFViewer pdfViewer){
        this.pdfViewer = pdfViewer;
        drawingCoordinates = new ArrayList<>();
        erasingCoordinates = new ArrayList<>();
    }

    private void onMousePressed(double x , double y , Paint color , double size) {
        // Start drawing when the mouse button is pressed
        gc.beginPath();
        gc.moveTo(x, y);
        gc.setStroke(color);
        gc.setLineWidth(size);
        drawingCoordinates.add(new MouseCoordinate(x,y));
    }

    private void onMouseDragged(double x , double y) {
        if (pdfViewer.getPdfViewerToolController().getCurrentPdfTool() == PDFTool.DRAW) {
            gc.lineTo(x, y);
            gc.stroke();
            drawingCoordinates.add(new MouseCoordinate(x,y));
        }
        else if(pdfViewer.getPdfViewerToolController().getCurrentPdfTool() == PDFTool.ERASER) {
            gc.clearRect(x,y,prefferedEraserSize,prefferedEraserSize);
            erasingCoordinates.add(new MouseCoordinate(x,y));
        }
    }

    private void onMouseExited() {
        System.out.println(drawingCoordinates);
        drawingCoordinates.clear();
        erasingCoordinates.clear();
    }

    public void onMousePressed(MouseEvent event) {
        onMousePressed(event.getX(), event.getY() , prefferedPenColor , prefferedPenSize);
    }

    public void onMouseDragged(MouseEvent event) {
        onMouseDragged(event.getX(), event.getY());
    }

    public void onMouseExited(MouseEvent event) {
        onMouseExited();
    }

    public void draw(MouseCoordinate[] mouseCoordinates, Color color, double size) {
        pdfViewer.getPdfViewerToolController().setCurrentPdfTool(PDFTool.DRAW);
        pdfViewer.getPdfViewerToolController().setCurrentDrawSize((int) size);
        // TODO control color.toString()
        pdfViewer.getPdfViewerToolController().setCurrentDrawColor(color.toString());
        onMousePressed(mouseCoordinates[0].x , mouseCoordinates[0].y , prefferedPenColor , prefferedPenSize);
        for (MouseCoordinate draw : mouseCoordinates) {
            onMouseDragged(mouseCoordinates[0].x , mouseCoordinates[0].y);
        }
    }

    public void erase(MouseCoordinate[] mouseCoordinates, double size) {
        pdfViewer.getPdfViewerToolController().setCurrentPdfTool(PDFTool.ERASER);
        pdfViewer.getPdfViewerToolController().setCurrentEraserSize((int) size);
        for (MouseCoordinate draw : mouseCoordinates) {
            onMouseDragged(mouseCoordinates[0].x , mouseCoordinates[0].y);
        }
    }

    public void reloadGraphicsContext() {
        if (viewArea != null && viewArea.getContent() instanceof Group) {
            Group group = (Group) viewArea.getContent();
            if (!group.getChildren().isEmpty()) {
                Node firstChild = group.getChildren().get(0);
                if (firstChild instanceof PDFPage) {
                    PDFPage pdfPage = (PDFPage) firstChild;
                    if (pdfPage.canvas != null) {
                        this.gc = pdfPage.canvas.getGraphicsContext2D();
                    }
                }
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
