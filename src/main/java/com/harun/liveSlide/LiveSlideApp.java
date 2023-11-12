package com.harun.liveSlide;

import com.harun.liveSlide.model.Participant;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;


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

        // Temporary initializer
        Participant[] participants  = new Participant[40];
        for(int i = 0; i < participants.length; i++) {
            participants[i] = new Participant(i,"Harun Eren Özkaya");
        }
        main.getParticipantTab().setParticipants(participants);
        main.getParticipantTab().changeParticipantRequestStatus(0,true);
        main.getParticipantTab().changeParticipantRequestStatus(1,true);
        main.getParticipantTab().changeParticipantRequestStatus(2,true);
    }



    public static void main(String[] args) {
        launch();
    }
}