package com.harun.liveSlide.components.meetingTopBar;

import com.harun.liveSlide.components.meetingTopBar.meetingTimer.MeetingTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MeetingTopBar extends Pane {
    private MeetingTimer meetingTimer;
    public MeetingTopBar() {
        this.getStyleClass().add("meeting-top-bar");
        setupLayout();
    }

    private void setupLayout() {
        HBox hBox = new HBox();
        this.getChildren().add(hBox);
        hBox.prefWidthProperty().bind(this.widthProperty());
        hBox.prefHeightProperty().bind(this.heightProperty());
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(20));

        meetingTimer = new MeetingTimer();
        hBox.getChildren().add(meetingTimer);
        meetingTimer.startTimer(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
