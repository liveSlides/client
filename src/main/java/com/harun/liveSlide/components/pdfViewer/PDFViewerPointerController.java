package com.harun.liveSlide.components.pdfViewer;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class PDFViewerPointerController {
    private PDFViewer pdfViewer;
    private Shape pointerShape;

    public PDFViewerPointerController(PDFViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
        updatePointerShape();
    }

    // Update pointer shape when page is changed
    public void updatePointerShape(){
        ScrollPane viewArea = pdfViewer.viewArea;
        if (viewArea != null && viewArea.getContent() instanceof PDFPageContainer) {
            PDFPageContainer pdfPageContainer = (PDFPageContainer) viewArea.getContent();
            pointerShape = pdfPageContainer.getPointerShape();
        }
    }

    public void point(double x , double y) {
        if (pointerShape != null){
            pointerShape.setLayoutX(x);
            pointerShape.setLayoutY(y);
        }
    }

    public void isPointerVisible(boolean isVisible) {
        pointerShape.setVisible(isVisible);
    }
}
