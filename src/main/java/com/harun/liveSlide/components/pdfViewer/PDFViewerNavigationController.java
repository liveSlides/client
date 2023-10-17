package com.harun.liveSlide.components.pdfViewer;

import javafx.event.ActionEvent;

public class PDFViewerNavigationController {
    private PDFViewer pdfViewer;
    private int currentPageIndex = 1;
    private int pageCount;

    public PDFViewerNavigationController(PDFViewer pdfViewer , int pageCount){
        this.pdfViewer = pdfViewer;
        this.pageCount = pageCount;
    }

    private void showPage() {
        pdfViewer.viewArea.setContent(pdfViewer.pdfPages.get(currentPageIndex-1));
        pdfViewer.toolBar.updatePageIndicator(currentPageIndex , pdfViewer.pdfPages.size());
        pdfViewer.reloadGraphicsContext();
    }

    public void goPage(int index) {
        if (index < 1 || index > pageCount)
            return;

        currentPageIndex = index;
        showPage();
        manageNavigationButtons();
    }

    public void goBackPage() {
        if (currentPageIndex == 1 || pageCount == 0){
            return;
        }

        currentPageIndex--;
        showPage();
        manageNavigationButtons();
    }

    public void goNextPage() {
        if (currentPageIndex == pageCount || pageCount == 0){
            return;
        }

        currentPageIndex++;
        showPage();
        manageNavigationButtons();
    }

    public void goBackPage(ActionEvent actionEvent) {
        goBackPage();
    }

    public void goNextPage(ActionEvent actionEvent) {
        goNextPage();
    }

    private void manageNavigationButtons() {
        pdfViewer.toolBar.setDisableBackButton(false);
        pdfViewer.toolBar.setDisableNextButton(false);

        if (currentPageIndex == 1) {
            pdfViewer.toolBar.setDisableBackButton(true);
        }
        if (currentPageIndex == pageCount) {
            pdfViewer.toolBar.setDisableNextButton(true);
        }
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }
}
