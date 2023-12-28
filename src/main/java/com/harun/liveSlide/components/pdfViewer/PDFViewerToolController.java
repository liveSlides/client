package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import javafx.event.ActionEvent;

public class PDFViewerToolController {
    private final PDFViewer pdfViewer;
    private PDFTool currentPdfTool;
    private PenEraserSize currentDrawSize;
    private PenEraserSize currentEraserSize;
    private PenColor currentDrawColor;
    private boolean isFullScreen = false;

    public PDFViewerToolController(PDFViewer pdfViewer) {
        this.pdfViewer = pdfViewer;
    }

    public PDFTool getCurrentPdfTool() {
        return currentPdfTool;
    }

    public void setCurrentPdfTool(PDFTool newPdfTool) {
        if (currentPdfTool == newPdfTool)
            return;

        this.currentPdfTool = newPdfTool;
        if (pdfViewer.viewArea.getContent() != null) {
            PDFPageContainer pdfPageContainer = (PDFPageContainer) pdfViewer.viewArea.getContent();
            PDFPage pdfPage = pdfPageContainer.getPDFPage();

            if (newPdfTool == PDFTool.CURSOR) {
                if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER)
                    pdfViewer.viewArea.setPannable(true);
                pdfPage.canvas.setMouseTransparent(true);
                pdfViewer.toolBar.setCursorSelected();
            }
            else if(newPdfTool == PDFTool.POINTER){
                pdfViewer.viewArea.setPannable(false);
                pdfPage.canvas.setMouseTransparent(false);
                pdfViewer.toolBar.setPointerSelected();
            }
            else if(newPdfTool == PDFTool.DRAW){
                pdfViewer.viewArea.setPannable(false);
                if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER)
                    pdfPage.canvas.setMouseTransparent(false);
                else
                    pdfPage.canvas.setMouseTransparent(true);
                pdfViewer.toolBar.setDrawSelected();
            }
            else if(newPdfTool == PDFTool.ERASER){
                pdfViewer.viewArea.setPannable(false);
                if (GlobalVariables.userType == UserType.HOST_PRESENTER || GlobalVariables.userType == UserType.PARTICIPANT_PRESENTER)
                    pdfPage.canvas.setMouseTransparent(false);
                else
                    pdfPage.canvas.setMouseTransparent(true);
                pdfViewer.toolBar.setEraserSelected();
            }
        }
    }

    public void setCurrentDrawSize(PenEraserSize size) {
        if (size == currentDrawSize)
            return;

        pdfViewer.getPdfViewerDrawController().setPenSize(PenEraserSizeConverter.convertSize(size));

        switch (size) {
            case SMALL :
                pdfViewer.toolBar.drawSmallSizeSelected();
                break;
            case MEDIUM:
                pdfViewer.toolBar.drawMediumSizeSelected();
                break;
            case BIG:
                pdfViewer.toolBar.drawBigSizeSelected();
                break;
        }

        currentDrawSize = size;
    }

    public void setCurrentEraserSize(PenEraserSize size) {
        if (size == currentEraserSize)
            return;

        pdfViewer.getPdfViewerDrawController().setEraserSize(PenEraserSizeConverter.convertSize(size) * 30);

        switch (size) {
            case SMALL :
                pdfViewer.toolBar.eraserSmallSizeSelected();
                break;
            case MEDIUM :
                pdfViewer.toolBar.eraserMediumSizeSelected();
                break;
            case BIG:
                pdfViewer.toolBar.eraserBigSizeSelected();
                break;
        }

        currentEraserSize = size;
    }

    public void setCurrentDrawColor(PenColor color) {
        if (currentDrawColor == color)
            return;

        pdfViewer.getPdfViewerDrawController().setPenColor(PenColorConverter.convertColor(color));

        if (color == PenColor.BLACK) {
            pdfViewer.toolBar.drawBlackSelected();
        }
        else if (color == PenColor.RED) {
            pdfViewer.toolBar.drawRedSelected();
        }
        else if (color == PenColor.BLUE) {
            pdfViewer.toolBar.drawBlueSelected();
        }

        currentDrawColor = color;
    }


    public void showFullScreen(ActionEvent actionEvent) {
        setFullScreen(!isFullScreen);
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        pdfViewer.showFullScreen(isFullScreen);
    }

}
