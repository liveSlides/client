package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import javafx.event.ActionEvent;

import java.io.IOException;

public class PDFViewerNavigationController {
    private PDFViewer pdfViewer;
    private int currentPageIndex = 1;
    private int pageStartIndex = 1;
    private int pageEndIndex = 10;
    private int pageCount;

    public PDFViewerNavigationController(PDFViewer pdfViewer , int pageCount){
        this.pdfViewer = pdfViewer;
        this.pageCount = pageCount;
    }

    private void showPage() {
        if (currentPageIndex > pageEndIndex || currentPageIndex < pageStartIndex) {
            try {
                pdfViewer.loadPDF(pdfViewer.currentFilePath,currentPageIndex);
                if (GlobalVariables.userType == UserType.PARTICIPANT_SPECTATOR || GlobalVariables.userType == UserType.HOST_SPECTATOR){
                    pdfViewer.getPdfViewerObserver().synchSlide();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            pdfViewer.viewArea.setContent(pdfViewer.pdfPages.get(currentPageIndex - pageStartIndex));
            pdfViewer.toolBar.updateZoomRateLabelText(pdfViewer.pdfPages.get(currentPageIndex - pageStartIndex).getPDFPage().getCurrentZoomRate());
        }
        pdfViewer.toolBar.updatePageIndicatorLabelText(currentPageIndex , pageCount);
        pdfViewer.reloadGraphicsContext();
        pdfViewer.reloadPointerShape();
    }

    public void goPage(int index) {
        if (index < 1 || index > pageCount)
            return;

        currentPageIndex = index;
        showPage();
        manageNavigationButtons();
    }

    public boolean goBackPage() {
        if (currentPageIndex == 1 || pageCount == 0){
            return false;
        }

        currentPageIndex--;
        showPage();
        manageNavigationButtons();
        return true;
    }

    public boolean goNextPage() {
        if (currentPageIndex == pageCount || pageCount == 0){
            return false;
        }

        currentPageIndex++;
        showPage();
        manageNavigationButtons();
        return true;
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

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPageStartIndex() {
        return pageStartIndex;
    }

    public void setPageStartIndex(int pageStartIndex) {
        this.pageStartIndex = pageStartIndex;
    }

    public int getPageEndIndex() {
        return pageEndIndex;
    }

    public void setPageEndIndex(int pageEndIndex) {
        this.pageEndIndex = pageEndIndex;
    }
}
