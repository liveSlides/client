package com.harun.liveSlide.components.pdfViewer;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.MouseCoordinate;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.awt.image.BufferedImage;

public class PDFPage extends StackPane {
    public PDFViewer pdfViewer;
    public PDFViewerDrawController pdfViewerDrawController;
    public PDFViewerPointerController pdfViewerPointerController;
    public ImageView image;
    public Canvas canvas;
    private Integer currentZoomRate = 100;
    public BufferedImage bufferedImage;

    public PDFPage() {
        this.image = null;
        this.canvas = null;
    }

    public PDFPage(PDFViewer pdfViewer , ImageView image , Canvas canvas , PDFViewerDrawController pdfViewerDrawController , PDFViewerPointerController pdfViewerPointerController, BufferedImage bim) {
        if (image != null && pdfViewerDrawController != null) {
            this.pdfViewer = pdfViewer;
            this.image = image;
            this.canvas = canvas;
            canvas.setWidth(image.getFitWidth());
            canvas.setHeight(image.getFitHeight());
            this.pdfViewerDrawController = pdfViewerDrawController;
            this.pdfViewerPointerController = pdfViewerPointerController;
            this.bufferedImage = bim;
            setupLayout();
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public ImageView getImage() {
        return image;
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
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            pdfViewer.canvasPressed(new MouseCoordinate(event.getX(),event.getY()));
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            pdfViewer.canvasDragged(new MouseCoordinate(event.getX(),event.getY()));
        });
    }

    public Integer getCurrentZoomRate() {
        return currentZoomRate;
    }

    public void setCurrentZoomRate(Integer currentZoomRate) {
        this.currentZoomRate = currentZoomRate;
    }
}
