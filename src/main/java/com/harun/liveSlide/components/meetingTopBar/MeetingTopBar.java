package com.harun.liveSlide.components.meetingTopBar;

import com.harun.liveSlide.screens.mainScreen.MainScreen;
import com.harun.liveSlide.components.meetingTopBar.meetingTimer.MeetingTimer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MeetingTopBar extends ToolBar {
    private final Stage stage;
    private final MainScreen mainScreen;
    private final double prefHeight;
    private final MeetingTopBarController controller;
    private MeetingTimer meetingTimer;
    private Button leaveButton;
    private Button takeControlButton;
    private Button requestControlButton;
    private Button participantButton;

    public MeetingTopBar(Stage stage , MainScreen mainScreen, double prefHeight , double prefWidth) {
        controller = new MeetingTopBarController(this);
        this.stage = stage;
        this.mainScreen = mainScreen;
        this.prefHeight = prefHeight;
        this.setPrefWidth(prefWidth);
        this.getStyleClass().add("meeting-top-bar");
        setupLayout();
    }
    private void setupLayout() {
        this.setPadding(new Insets(20));

        //Meeting Timer
        meetingTimer = new MeetingTimer();
        this.getItems().add(meetingTimer);
        meetingTimer.startTimer(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        //Left Spacer
        Pane spacer = new Pane();
        HBox.setHgrow(
                spacer,
                Priority.SOMETIMES
        );
        this.getItems().add(spacer);

        //Take Control Back Button
        takeControlButton = new Button("");
        takeControlButton.setGraphic(getButtonIcon("/img/takeControlBack.png", prefHeight));
        takeControlButton.getStyleClass().add("take-control-button");
        takeControlButton.setPadding(new Insets(5,8,5,8));
        takeControlButton.setOnAction(controller::takeControlBack);
        takeControlButton.setTooltip(new Tooltip("Take control back"));
        this.getItems().add(takeControlButton);

        //Request Control Button
        requestControlButton = new Button();
        requestControlButton.getStyleClass().add("request-control-button");
        requestControlButton.setGraphic(getButtonIcon("/img/hand.png", prefHeight));
        requestControlButton.setOnAction(controller::requestControl);
        requestControlButton.setPadding(new Insets(5,8,5,8));
        requestControlButton.setTooltip(new Tooltip("Request control"));
        this.getItems().add(requestControlButton);

        //Participants Buttons
        participantButton = new Button();
        participantButton.getStyleClass().add("participant-button");
        participantButton.setGraphic(getButtonIcon("/img/participants.png", prefHeight));
        participantButton.setOnAction(controller::showParticipants);
        participantButton.setPadding(new Insets(5,8,5,8));
        participantButton.setTooltip(new Tooltip("Show participant list"));
        this.getItems().add(participantButton);

        //Leave Button
        leaveButton = new Button("Leave");
        leaveButton.getStyleClass().add("leave-button");
        leaveButton.setPadding(new Insets(11,40,11,40));
        leaveButton.setOnAction(controller::leave);
        this.getItems().add(leaveButton);
    }

    public void changeHandIcon(String path) {
        requestControlButton.setGraphic(getButtonIcon(path, prefHeight));
    }

    private ImageView getButtonIcon(String path , double prefHeight) {
        ImageView iconImage = new ImageView(new Image(String.valueOf(getClass().getResource(path))));
        iconImage.setFitWidth(prefHeight / 2);
        iconImage.setFitHeight(prefHeight / 2);
        return iconImage;
    }

    public void setVisibleControlBackButton(boolean visibility) {
        takeControlButton.setVisible(visibility);
    }

    public Stage getStage() {
        return stage;
    }

    public MainScreen getMainWindow() {
        return mainScreen;
    }

    public Button getRequestControlButton() {
        return requestControlButton;
    }
}
