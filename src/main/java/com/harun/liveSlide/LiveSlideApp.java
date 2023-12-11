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
        Scene activeScene = showLoginScreen(stage);
        stage.setScene(activeScene);
        stage.show();
    }

    public Scene showLoginScreen(Stage stage) {
        double sceneWidth = 600;
        double sceneHeight = 400;
        LoginScreen login = new LoginScreen(sceneWidth,sceneHeight);
        Scene loginScene = new Scene(login,sceneWidth,sceneHeight);
        loginScene.getStylesheets().add("style.css");
        stage.setMaxHeight(sceneHeight);
        stage.setMaxWidth(sceneWidth);

        loginScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            login.setResponsiveWidth(newValue.doubleValue());
        });

        loginScene.heightProperty().addListener((observable, oldValue, newValue) -> {
            login.setResponsiveHeight(newValue.doubleValue());
        });

        return loginScene;
    }
    public Scene showMainScreen(Stage stage) throws IOException {
        double sceneWidth = 1680;
        double sceneHeight = 1000;

        MainScreen main = new MainScreen(stage , sceneWidth , sceneHeight);
        Scene mainScene = new Scene(main, sceneWidth, sceneHeight);
        mainScene.getStylesheets().add("style.css");

        mainScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            main.setResponsiveWidth(newValue.doubleValue());
        });

        mainScene.heightProperty().addListener((observable, oldValue, newValue) -> {
            main.setResponsiveHeight(newValue.doubleValue());
        });

        // Temporary initializer
        Participant[] participants  = new Participant[40];
        for(int i = 0; i < participants.length; i++) {
            participants[i] = new Participant(i,"Harun Eren Özkaya");
        }
        main.getParticipantTab().setParticipants(participants);
        main.getParticipantTab().changeParticipantRequestStatus(0,true);
        main.getParticipantTab().changeParticipantRequestStatus(1,true);
        main.getParticipantTab().changeParticipantRequestStatus(2,true);

        return mainScene;
    }



    public static void main(String[] args) {
        launch();
    }
}