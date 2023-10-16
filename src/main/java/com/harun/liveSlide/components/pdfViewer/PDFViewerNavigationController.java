package com.harun.liveSlide.components.pdfViewer;

import javafx.event.ActionEvent;

public class PDFViewerNavigationController {
    private PDFViewer pdfViewer;
    private int pageIndex = 1;
    private int pageCount;

    public PDFViewerNavigationController(PDFViewer pdfViewer , int pageCount){
        this.pdfViewer = pdfViewer;
        this.pageCount = pageCount;
    }

    public void goBackPage(ActionEvent actionEvent) {
        pdfViewer.showPage(--pageIndex);

        if (pageIndex == 1) {
            pdfViewer.toolBar.setDisableBackButton(true);
        }
        if (pageIndex != pageCount) {
            pdfViewer.toolBar.setDisableNextButton(false);
        }
    }

    public void goNextPage(ActionEvent actionEvent) {
        pdfViewer.showPage(++pageIndex);

        if (pageIndex == pageCount) {
            pdfViewer.toolBar.setDisableNextButton(true);
        }
        if (pageIndex == 2) {
            pdfViewer.toolBar.setDisableBackButton(false);
        }
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
