module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires javafx.swing;

    opens com.harun.liveSlide to javafx.fxml;
    exports com.harun.liveSlide;
    exports com.harun.liveSlide.components.pdfViewer;
    opens com.harun.liveSlide.components.pdfViewer to javafx.fxml;
    exports com.harun.liveSlide.network;
    opens com.harun.liveSlide.network to javafx.fxml;
    exports com.harun.liveSlide.enums;
    opens com.harun.liveSlide.enums to javafx.fxml;
    exports com.harun.liveSlide.screens.mainScreen;
    opens com.harun.liveSlide.screens.mainScreen to javafx.fxml;
}