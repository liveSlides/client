package com.harun.liveSlide;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.screens.loginScreen.LoginScreen;
import com.harun.liveSlide.screens.mainScreen.MainScreen;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LiveSlideManager {
    private LoginScreen loginScreen;
    private MainScreen mainScreen;

    private final Stage stage;

    public LiveSlideManager(Stage stage) {
        this.stage = stage;
    }

    public void showLoginScreen() {
        double sceneWidth = 600;
        double sceneHeight = 400;
        loginScreen = new LoginScreen(this,sceneWidth,sceneHeight);
        Scene loginScene = new Scene(loginScreen,sceneWidth,sceneHeight);
        loginScene.getStylesheets().add("style.css");

        loginScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            loginScreen.setResponsiveWidth(newValue.doubleValue());
        });

        loginScene.heightProperty().addListener((observable, oldValue, newValue) -> {
            loginScreen.setResponsiveHeight(newValue.doubleValue());
        });

        stage.setScene(loginScene);
        stage.show();
    }

    public void showMainScreen() {

        double sceneWidth = 1680;
        double sceneHeight = 1000;

        mainScreen = new MainScreen(this ,stage , sceneWidth , sceneHeight);
        Scene mainScene = new Scene(mainScreen, sceneWidth, sceneHeight);
        mainScene.getStylesheets().add("style.css");

        mainScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            mainScreen.setResponsiveWidth(newValue.doubleValue());
        });

        mainScene.heightProperty().addListener((observable, oldValue, newValue) -> {
            mainScreen.setResponsiveHeight(newValue.doubleValue());
        });

        Platform.runLater(() -> {
            stage.setScene(mainScene);
        });
    }
}
