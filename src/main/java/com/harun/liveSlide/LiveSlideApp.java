package com.harun.liveSlide;

import com.harun.liveSlide.model.Participant;
import com.harun.liveSlide.screens.loginScreen.LoginScreen;
import com.harun.liveSlide.screens.mainScreen.MainScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class LiveSlideApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Live Slide");
        LiveSlideManager liveSlideManager = new LiveSlideManager(stage);
        liveSlideManager.showLoginScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}