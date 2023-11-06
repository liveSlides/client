package com.harun.liveSlide.components.pdfViewer;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class PDFPage extends StackPane {
    public PDFViewerDrawController pdfViewerDrawController;
    public PDFViewerPointerController pdfViewerPointerController;
    public ImageView image;
    public Canvas canvas;

    public PDFPage() {
        this.image = null;
        this.canvas = null;
    }

    public PDFPage(ImageView image , Canvas canvas , PDFViewerDrawController pdfViewerDrawController , PDFViewerPointerController pdfViewerPointerController) {
        if (image != null && pdfViewerDrawController != null) {
            this.image = image;
            this.canvas = canvas;
            canvas.setWidth(image.getFitWidth());
            canvas.setHeight(image.getFitHeight());
            this.pdfViewerDrawController = pdfViewerDrawController;
            this.pdfViewerPointerController = pdfViewerPointerController;
            setupLayout();
        }
    }

    void setImageView(ImageView image){
        this.image = image;
        if (image != null) {
            this.canvas = new Canvas(image.getFitWidth(), image.getFitHeight());
            setupLayout();
        }
    }

    private void setupLayout(){
        this.setId("pdf-page");
        setMargin(image,new Insets(15));
        this.getChildren().add(image);
        this.getChildren().add(canvas);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, pdfViewerDrawController::onMousePressed);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, pdfViewerDrawController::onMouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, pdfViewerDrawController::onMouseExited);
    }
}
