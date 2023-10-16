package com.harun.liveSlide;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LiveSlideApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        MainWindow main = new MainWindow();
        Scene scene = new Scene(main, 1920, 1080);
        scene.getStylesheets().add("style.css");
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        //      stage.setFullScreen(true);

        main.pdfViewer.loadPDF("/Users/harun/Desktop/Git.pdf");
    }

    public static void main(String[] args) {
        launch();
    }
}