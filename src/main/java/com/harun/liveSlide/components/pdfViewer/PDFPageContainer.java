package com.harun.liveSlide.components.pdfViewer;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PDFPageContainer extends Group {
    private final PDFPage pdfPage;
    private final Circle pointerShape = new Circle(0,0,5, Color.RED);;

    public PDFPageContainer(PDFPage pdfPage){
        super(pdfPage);
        this.pdfPage = pdfPage;
        this.getChildren().add(pointerShape);
        this.pointerShape.setVisible(false);
    }

    public PDFPage getPDFPage(){
        return pdfPage;
    }

    public Circle getPointerShape(){
        return pointerShape;
    }
}
