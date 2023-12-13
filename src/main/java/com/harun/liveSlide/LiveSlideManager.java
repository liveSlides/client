package com.harun.liveSlide;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.screens.loginScreen.LoginScreen;
import com.harun.liveSlide.screens.mainScreen.MainScreen;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LiveSlideManager {

    private final Stage stage;

    public LiveSlideManager(Stage stage) {
        this.stage = stage;
    }

    public void showLoginScreen() {
        double sceneWidth = 600;
        double sceneHeight = 400;
        LoginScreen login = new LoginScreen(this,sceneWidth,sceneHeight);
        Scene loginScene = new Scene(login,sceneWidth,sceneHeight);
        loginScene.getStylesheets().add("style.css");

        loginScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            login.setResponsiveWidth(newValue.doubleValue());
        });

        loginScene.heightProperty().addListener((observable, oldValue, newValue) -> {
            login.setResponsiveHeight(newValue.doubleValue());
        });

        stage.setScene(loginScene);
        stage.show();
    }

    public void showMainScreen() {

        double sceneWidth = 1680;
        double sceneHeight = 1000;

        MainScreen mainScreen = new MainScreen(this ,stage , sceneWidth , sceneHeight);
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

        // Temporary initializer
        /*
        Participant[] participants  = new Participant[40];
        for(int i = 0; i < participants.length; i++) {
            participants[i] = new Participant(i,"Harun Eren Özkaya");
        }
        main.getParticipantTab().setParticipants(participants);
        main.getParticipantTab().changeParticipantRequestStatus(0,true);
        main.getParticipantTab().changeParticipantRequestStatus(1,true);
        main.getParticipantTab().changeParticipantRequestStatus(2,true);
        */
    }
}
