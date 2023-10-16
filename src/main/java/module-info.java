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
}