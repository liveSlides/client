package com.harun.liveSlide.components.pdfViewer;

enum PenEraserSize {
    SMALL,
    MEDIUM,
    BIG
}

public class PenEraserSizeConverter {
    public static int convertSize(PenEraserSize size) {
        switch (size) {
            case SMALL :
                return 1;
            case MEDIUM :
                return 2;
            case BIG :
                return 3;
        }
        return 1;
    }
}
