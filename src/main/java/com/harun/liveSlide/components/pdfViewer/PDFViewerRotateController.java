package com.harun.liveSlide.components.pdfViewer;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class PDFViewerRotateController {
    private ScrollPane viewArea;

    public void rotate() {
        if (viewArea != null && viewArea.getContent() != null) {
            PDFPageContainer pdfPageContainer = (PDFPageContainer) viewArea.getContent();
            PDFPage pdfPage = pdfPageContainer.getPDFPage();

            Rotate rotate = new Rotate();
            rotate.setPivotX(pdfPage.getWidth() / 2);
            rotate.setPivotY(pdfPage.getHeight() / 2);
            rotate.setAngle(90);
            pdfPage.getTransforms().add(rotate);

            viewArea.setVvalue(0.5);
            pdfPage.setMinHeight(viewArea.getViewportBounds().getWidth());
        }
    }

    public void setViewArea(ScrollPane viewArea) {
        this.viewArea = viewArea;
    }

    public void rotate(ActionEvent actionEvent) {
        rotate();
    }
}
