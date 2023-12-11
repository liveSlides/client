package com.harun.liveSlide.screens.loginScreen;

import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

public class LoginScreen extends Pane {
    private final VBox mainVbox;
    private final Label logo;
    private final TextField nameTextField;

    private final TextField meetingIdTextField;
    private final Button joinMeeetingButton;
    private final TextField nameTextField2;
    private final Button hostMeetingButton;

    public LoginScreen(double sceneWidth , double sceneHeight) {
        this.setId("login-screen");

        // Main VBox
        mainVbox = new VBox();
        mainVbox.setPrefWidth(sceneWidth);
        mainVbox.setPrefHeight(sceneHeight);
        mainVbox.setPadding(new Insets(10,20,10,20));
        mainVbox.setAlignment(Pos.CENTER);
        this.getChildren().add(mainVbox);

        //Logo
        logo = new Label("LiveSlide");
        mainVbox.getChildren().add(logo);

        //Spacer
        Pane spacer0 = new Pane();
        spacer0.setPrefHeight(30);
        mainVbox.getChildren().add(spacer0);

        // Text Field
        nameTextField = new TextField();
        nameTextField.setMaxWidth(mainVbox.getPrefWidth() / 2);
        nameTextField.setPromptText("Name");
        nameTextField.setAccessibleText("Name");
        nameTextField.setFocusTraversable(false);
        mainVbox.getChildren().add(nameTextField);

        //Spacer
        Pane spacer1 = new Pane();
        spacer1.setPrefHeight(10);
        mainVbox.getChildren().add(spacer1);

        // Text Field
        meetingIdTextField = new TextField();
        meetingIdTextField.setMaxWidth(mainVbox.getPrefWidth() / 2);
        meetingIdTextField.setPromptText("Meeting ID");
        meetingIdTextField.setAccessibleText("Meeting ID");
        meetingIdTextField.setFocusTraversable(false);
        mainVbox.getChildren().add(meetingIdTextField);

        //Spacer
        Pane spacer2 = new Pane();
        spacer2.setPrefHeight(10);
        mainVbox.getChildren().add(spacer2);

        //Join a Meeting Button
        joinMeeetingButton = new Button("Join Meeting");
        joinMeeetingButton.setPrefWidth(mainVbox.getPrefWidth() / 4);
        joinMeeetingButton.setPrefHeight(30);
        joinMeeetingButton.setOnAction(event -> {

        });
        mainVbox.getChildren().add(joinMeeetingButton);

        //Spacer
        Pane spacer3 = new Pane();
        spacer3.setPrefHeight(30);
        mainVbox.getChildren().add(spacer3);

        // Text Field
        nameTextField2 = new TextField();
        nameTextField2.setMaxWidth(mainVbox.getPrefWidth() / 2);
        nameTextField2.setPromptText("Name");
        nameTextField2.setAccessibleText("Name");
        nameTextField2.setFocusTraversable(false);
        mainVbox.getChildren().add(nameTextField2);

        //Spacer
        Pane spacer4 = new Pane();
        spacer4.setPrefHeight(10);
        mainVbox.getChildren().add(spacer4);

        //Host a Meeting Button
        hostMeetingButton = new Button("Host Meeting");
        hostMeetingButton.setPrefWidth(mainVbox.getPrefWidth() / 4);
        hostMeetingButton.setPrefHeight(30);
        mainVbox.getChildren().add(hostMeetingButton);

    }

    public void setResponsiveWidth(double width) {
        mainVbox.setPrefWidth(width);
    }

    public void setResponsiveHeight(double height) {
        mainVbox.setPrefHeight(height);
    }
}
