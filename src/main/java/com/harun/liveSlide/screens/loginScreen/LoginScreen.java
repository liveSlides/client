package com.harun.liveSlide.screens.loginScreen;

import com.harun.liveSlide.LiveSlideManager;
import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.network.login.SessionInitializeType;
import com.harun.liveSlide.network.NetworkLoginManager;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class LoginScreen extends Pane {
    private final VBox mainVbox;
    private final Label logo;
    private final TextField nameTextField;

    private final TextField meetingIdTextField;
    private final Button joinMeeetingButton;
    private final TextField nameTextField2;
    private final Button hostMeetingButton;
    private final Label errorLabel;

    private final NetworkLoginManager networkLoginManager;
    private final LiveSlideManager liveSlideManager;

    public LoginScreen(LiveSlideManager liveSlideManager , double sceneWidth , double sceneHeight) {
        this.setId("login-screen");
        this.networkLoginManager = new NetworkLoginManager(this);
        this.liveSlideManager = liveSlideManager;

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
        logo.setId("login-logo");

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
            networkLoginManager.join(meetingIdTextField.getText(),nameTextField.getText());
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
        hostMeetingButton.setOnAction(event -> {
            networkLoginManager.host(nameTextField2.getText());
        });
        mainVbox.getChildren().add(hostMeetingButton);

        //Spacer
        Pane spacer5 = new Pane();
        spacer5.setPrefHeight(10);
        mainVbox.getChildren().add(spacer5);

        //Error Label
        errorLabel = new Label();
        mainVbox.getChildren().add(errorLabel);
    }

    public void setResponsiveWidth(double width) {
        mainVbox.setPrefWidth(width);
    }

    public void setResponsiveHeight(double height) {
        mainVbox.setPrefHeight(height);
    }

    public void showHostError(String message) {
        errorLabel.setText(message);
    }

    public void showJoinError(String message) {
        Platform.runLater(() -> {
            errorLabel.setText(message);
        });
    }
    public void showSessionIDInformationScreen(String sessionID) {
        double sceneWidth = 400;
        double sceneHeight = 200;

        Platform.runLater(() -> {
            Pane pane = new Pane();
            pane.setPrefWidth(sceneWidth);
            pane.setPrefHeight(sceneHeight);
            pane.setId("sessionIDAlert");

            VBox vbox = new VBox();
            vbox.setPrefHeight(pane.getPrefHeight());
            vbox.setPrefWidth(pane.getPrefWidth());
            vbox.setAlignment(Pos.CENTER);
            pane.getChildren().add(vbox);

            Label label = new Label("Meeting ID");
            label.setAlignment(Pos.CENTER);
            vbox.getChildren().add(label);

            TextField sessionIDField = new TextField(sessionID);
            sessionIDField.setEditable(false);
            sessionIDField.setBorder(Border.EMPTY);
            sessionIDField.setAlignment(Pos.CENTER);
            vbox.getChildren().add(sessionIDField);

            Stage sessionIDStage = new Stage();
            sessionIDStage.setMaxHeight(sceneHeight);
            sessionIDStage.setMaxWidth(sceneWidth);
            Scene scene = new Scene(pane,sceneWidth,sceneHeight);
            scene.getStylesheets().add("style.css");

            sessionIDStage.setScene(scene);
            sessionIDStage.show();
        });
    }

    public void showMainScreen(String sessionID , String sessionCreationTime , SessionInitializeType sessionInitializeType) {
        if (GlobalVariables.SESSION_ID != null || GlobalVariables.userType != null)
            return;

        switch (sessionInitializeType) {
            case HOST:
                GlobalVariables.userType = UserType.HOST_PRESENTER;
                break;
            case JOIN:
                GlobalVariables.userType = UserType.PARTICIPANT_SPECTATOR;
                break;
        }
        GlobalVariables.SESSION_ID = sessionID;
        GlobalVariables.SESSION_CREATION_TIME = sessionCreationTime;
        liveSlideManager.showMainScreen();
    }
}
