package com.harun.liveSlide.components.pdfViewer;

import javafx.scene.control.ScrollPane;

public class PDFViewerScrollController {
    private ScrollPane viewArea;

    public void scrollVerticallyTo(double vValue) {
        if (viewArea != null)
            viewArea.setVvalue(vValue);
    }

    public void scrollHorizontallyTo(double hValue) {
        if (viewArea != null)
            viewArea.setHvalue(hValue);
    }

    public void setViewArea(ScrollPane viewArea) {
        this.viewArea = viewArea;
    }
}
