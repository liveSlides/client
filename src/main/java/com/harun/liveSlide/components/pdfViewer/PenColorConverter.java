package com.harun.liveSlide.components.pdfViewer;
import javafx.scene.paint.Color;

enum PenColor {
    BLACK,
    RED,
    BLUE
}

public class PenColorConverter {
    public static Color convertColor(PenColor color) {
        switch (color) {
            case BLACK :
                return Color.BLACK;
            case RED :
                return Color.RED;
            case BLUE :
                return Color.BLUE;
        }
        return Color.BLACK;
    }
}
