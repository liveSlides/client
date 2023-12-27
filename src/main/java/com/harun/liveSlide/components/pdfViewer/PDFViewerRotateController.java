package com.harun.liveSlide.components.pdfViewer;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class PDFViewerRotateController {
    private final PDFViewer pdfViewer;
    private int rotateRate = 0;

    PDFViewerRotateController(PDFViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
    }

    public void rotate() {
        if (pdfViewer.viewArea != null && pdfViewer.viewArea.getContent() != null) {
            PDFPageContainer pdfPageContainer = (PDFPageContainer) pdfViewer.viewArea.getContent();
            PDFPage pdfPage = pdfPageContainer.getPDFPage();

            Rotate rotate = new Rotate();
            rotate.setPivotX(pdfPage.getWidth() / 2);
            rotate.setPivotY(pdfPage.getHeight() / 2);
            rotate.setAngle(90);
            pdfPage.getTransforms().add(rotate);

            pdfViewer.scrollVerticallyTo(0.5);

            pdfPage.setMinHeight(pdfViewer.viewArea.getViewportBounds().getWidth());

            rotateRate = (rotateRate + 90) % 360;
        }
    }

    public void rotateToRotateRate(int rotateRate) {
        if (this.rotateRate == rotateRate)
            return;

        while (this.rotateRate != rotateRate)
            rotate();
    }

    public int getRotateRate() {
        return rotateRate;
    }

    public void setRotateRate(int rotateRate) {
        this.rotateRate = rotateRate;
    }
}
