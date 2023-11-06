package com.harun.liveSlide;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LiveSlideApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        double sceneWidth = 1680;
        double sceneHeight = 1000;
        MainWindow main = new MainWindow(stage , sceneWidth , sceneHeight);
        Scene scene = new Scene(main, sceneWidth, sceneHeight);
        scene.getStylesheets().add("style.css");
        stage.setTitle("Live Slide");
        stage.setScene(scene);
        stage.show();

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            main.setResponsiveWidth(newValue.doubleValue());
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            main.setResponsiveHeight(newValue.doubleValue());
        });


    }



    public static void main(String[] args) {
        launch();
    }
}