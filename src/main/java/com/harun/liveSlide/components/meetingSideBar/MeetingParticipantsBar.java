package com.harun.liveSlide.components.meetingSideBar;

import com.harun.liveSlide.model.Participant;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.Set;

public class MeetingParticipantsBar extends BorderPane {
    private final MeetingParticipantsBarController meetingParticipantsBarController;
    private final Pane topBar;
    private final Pane participantListArea;
    private final Label participantTitle;
    private final ScrollPane scrollPane;
    private final VBox vbox;

    public MeetingParticipantsBar() {
        this.meetingParticipantsBarController = new MeetingParticipantsBarController(this);
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
        participantTitle.layoutYProperty().bind(topBar.
                        heightProperty().
                        subtract(participantTitle.heightProperty()).
                        divide(2)
        );
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
        scrollPane.setContent(vbox);
    }

    public void show() {
        meetingParticipantsBarController.show();
    }

    public void hide() {
        meetingParticipantsBarController.hide();
    }

    public double getSideBarWidth() {
        return meetingParticipantsBarController.getSideBarWidth();
    }

    public boolean isShown() {
        return meetingParticipantsBarController.isShown();
    }

    public void setParticipants(Set<Participant> participants) {
        meetingParticipantsBarController.setParticipants(participants);
    }

    public void addParticipant(Participant participant) {
        meetingParticipantsBarController.addParticipant(participant);
    }

    public void removeParticipant(Participant participant) {
        meetingParticipantsBarController.removeParticipant(participant);
    }

    public void changeParticipantRequestStatus(String participantId ,boolean status) {
        meetingParticipantsBarController.changeParticipantRequestStatus(participantId,status);
    }

    public MeetingParticipantsBarController getMeetingSideBarController() {
        return meetingParticipantsBarController;
    }

    public VBox getVbox() {
        return vbox;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }
}
