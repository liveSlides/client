package com.harun.liveSlide.components.meetingSideBar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MeetingSideBar extends BorderPane {
    private final MeetingSideBarController meetingSideBarController;
    private final Pane topBar;
    private final Label participantTitle;

    public MeetingSideBar() {
        this.meetingSideBarController = new MeetingSideBarController(this);
        this.participantTitle = new Label("Participants");
        this.topBar = new Pane();
        this.getStyleClass().add("side-participant-tab");
        setupLayout();
    }

    private void setupLayout() {
        topBar.setPrefHeight(40);
        this.setTop(topBar);


        participantTitle.setText("Participants");
        participantTitle.layoutYProperty().bind(topBar.heightProperty().subtract(participantTitle.heightProperty()).divide(2));
        participantTitle.setPadding(new Insets(10));
        participantTitle.getStyleClass().add("side-participant-tab-title");
        topBar.getChildren().add(participantTitle);

    }

    public void show() {
        meetingSideBarController.show();
    }

    public void hide() {
        meetingSideBarController.hide();
    }

    public double getSideBarWidth() {
        return meetingSideBarController.getSideBarWidth();
    }

    public boolean isShown() {
        return meetingSideBarController.isShown();
    }

    public MeetingSideBarController getMeetingSideBarController() {
        return meetingSideBarController;
    }
}
