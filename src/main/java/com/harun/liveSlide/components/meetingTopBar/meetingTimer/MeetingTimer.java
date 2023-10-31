package com.harun.liveSlide.components.meetingTopBar.meetingTimer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MeetingTimer extends Label {
    private Timeline timeline;
    private String startTime;

    public MeetingTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Get the current time
            LocalTime currentTime = LocalTime.now();

            // Parse the start time string to LocalTime
            LocalTime parsedStartTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Calculate the time difference
            long secondsDiff = parsedStartTime.until(currentTime, java.time.temporal.ChronoUnit.SECONDS);
            if (secondsDiff < 0) {
                setText("Meeting has not started yet");
            } else {
                // Format the time difference as HH:mm:ss
                String timeDifference = String.format("%02d:%02d:%02d",
                        secondsDiff / 3600, (secondsDiff % 3600) / 60, secondsDiff % 60);
                setText(timeDifference);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        this.getStyleClass().add("meeting-timer");
    }

    public void startTimer(String startTime) {
        this.startTime = startTime;
        timeline.play();
    }
}
