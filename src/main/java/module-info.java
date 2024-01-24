module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires javafx.swing;
    requires javax.websocket.api;
    requires spring.messaging;
    requires spring.websocket;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires spring.core;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.services.s3;
    requires spring.beans;
    requires spring.context;

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
    opens com.harun.liveSlide.model to com.fasterxml.jackson.databind;
    opens com.harun.liveSlide.model.network to com.fasterxml.jackson.databind;
    opens com.harun.liveSlide.model.network.login to com.fasterxml.jackson.databind;
    opens com.harun.liveSlide.model.network.participantList to com.fasterxml.jackson.databind;
    opens com.harun.liveSlide.model.network.pdfFile to com.fasterxml.jackson.databind;
    opens com.harun.liveSlide.model.network.meeting to com.fasterxml.jackson.databind;
}