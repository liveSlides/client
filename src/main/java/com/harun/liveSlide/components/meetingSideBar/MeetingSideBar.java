package com.harun.liveSlide.components.meetingSideBar;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class MeetingSideBar extends BorderPane {
    private final MeetingSideBarController meetingSideBarController;
    private final Pane topBar;
    private final Pane participantListArea;
    private final Label participantTitle;
    private final ScrollPane scrollPane;
    private final VBox vbox;

    public MeetingSideBar() {
        this.meetingSideBarController = new MeetingSideBarController(this);
        this.participantTitle = new Label("Participants");
        this.topBar = new Pane();
        this.participantListArea = new Pane();
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
        participantTitle.prefWidthProperty().bind(this.prefWidthProperty());
        topBar.getChildren().add(participantTitle);

        participantListArea.setMinWidth(0);
        this.setCenter(participantListArea);

        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("participant-tab-scroll");
        scrollPane.prefHeightProperty().bind(participantListArea.heightProperty());
        scrollPane.prefWidthProperty().bind(this.prefWidthProperty());
        participantListArea.getChildren().add(scrollPane);

        vbox.setSpacing(10);
        vbox.getStyleClass().add("participant-tab-scroll");
        for(int i = 0; i < 40; i++) {
            ParticipantComponent participant = new ParticipantComponent("Harun Eren Özkaya",scrollPane.widthProperty());
            vbox.getChildren().add(participant);
            if (i % 2 == 0) {
                participant.raiseHand();
            }
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
