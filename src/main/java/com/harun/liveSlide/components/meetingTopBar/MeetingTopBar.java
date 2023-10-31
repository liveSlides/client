package com.harun.liveSlide.components.meetingTopBar;

import com.harun.liveSlide.components.meetingTopBar.meetingTimer.MeetingTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MeetingTopBar extends ToolBar {
    private final Stage stage;
    private MeetingTopBarController controller;
    private MeetingTimer meetingTimer;
    private Button leaveButton;
    private Button takeControlButton;

    public MeetingTopBar(Stage stage) {
        controller = new MeetingTopBarController(this);
        this.stage = stage;
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
        takeControlButton = new Button("Take Control");
        takeControlButton.getStyleClass().add("take-control-button");
        takeControlButton.setPadding(new Insets(10,30,10,30));
        //this.getItems().add(takeControlButton);

        //Leave Button
        leaveButton = new Button("Leave");
        leaveButton.getStyleClass().add("leave-button");
        leaveButton.setPadding(new Insets(10,40,10,40));
        leaveButton.setOnAction(controller::leave);
        this.getItems().add(leaveButton);
    }

    public Stage getStage() {
        return stage;
    }
}
