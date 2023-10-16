package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.model.MouseCoordinate;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PDFPage extends StackPane {
    public PDFViewerDrawController pdfViewerDrawController;
    public ImageView image;
    public Canvas canvas;
    private GraphicsContext gc;



    public PDFPage() {
        this.image = null;
        this.canvas = null;
    }

    public PDFPage(ImageView image , PDFViewerDrawController pdfViewerDrawController) {
        if (image != null && pdfViewerDrawController != null) {
            this.image = image;
            this.canvas = new Canvas(image.getFitWidth(), image.getFitHeight());
            this.pdfViewerDrawController = pdfViewerDrawController;
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
        this.setMargin(image,new Insets(15));
        this.getChildren().add(image);
        this.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, pdfViewerDrawController::onMousePressed);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, pdfViewerDrawController::onMouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, pdfViewerDrawController::onMouseExited);
    }
}
