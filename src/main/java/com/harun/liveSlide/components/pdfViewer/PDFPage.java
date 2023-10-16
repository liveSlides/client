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
    public ImageView image;
    public Canvas canvas;
    private GraphicsContext gc;
    private ArrayList<MouseCoordinate> drawingCoordinates;


    public PDFPage() {
        this.image = null;
        this.canvas = null;
    }

    public PDFPage(ImageView image) {
        this.image = image;
        if (image != null) {
            drawingCoordinates = new ArrayList<>();
            this.canvas = new Canvas(image.getFitWidth(), image.getFitHeight());
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
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, this::onMouseExited);
    }

    private void onMousePressed(MouseEvent event) {
        // Start drawing when the mouse button is pressed
        gc.beginPath();
        gc.moveTo(event.getX(), event.getY());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        drawingCoordinates.add(new MouseCoordinate(event.getX(),event.getY()));
    }

    private void onMouseDragged(MouseEvent event) {
        // Draw when the mouse is dragged
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();
        drawingCoordinates.add(new MouseCoordinate(event.getX(),event.getY()));
    }

    private void onMouseExited(MouseEvent event) {

        System.out.println(drawingCoordinates);
        drawingCoordinates.clear();
    }
}
