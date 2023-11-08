package com.harun.liveSlide.components.meetingSideBar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MeetingSideBar extends BorderPane {
    private final MeetingSideBarController meetingSideBarController;
    private final Pane topBar;
    private final Label participantTitle;
    private final ScrollPane scrollPane;
    private final VBox vbox;

    public MeetingSideBar() {
        this.meetingSideBarController = new MeetingSideBarController(this);
        this.participantTitle = new Label("Participants");
        this.topBar = new Pane();
        this.scrollPane = new ScrollPane();
        this.vbox = new VBox();
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

        Pane innerPane = new Pane();
        innerPane.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
        this.setCenter(innerPane);

        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPadding(new Insets(0,10,0,10));
        scrollPane.getStyleClass().add("participant-tab-scroll");
        scrollPane.prefHeightProperty().bind(innerPane.heightProperty());
        innerPane.getChildren().add(scrollPane);


        vbox.setSpacing(10);
        for(int i = 0; i < 40; i++) {
            vbox.getChildren().add(new ParticipantComponent("selam",meetingSideBarController.getSideBarWidth()));
        }
        scrollPane.setContent(vbox);

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
