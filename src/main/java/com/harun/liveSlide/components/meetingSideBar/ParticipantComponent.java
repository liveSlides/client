package com.harun.liveSlide.components.meetingSideBar;

import com.harun.liveSlide.model.Participant;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ParticipantComponent extends Pane {
    private final double COMPONENT_HEIGHT = 40;

    private boolean isControlRequested = false;
    private final MeetingParticipantsBarController meetingParticipantsBarController;
    private final Participant participant;
    private final ImageView participantIcon;
    private final Label participantName;
    private final Button requestControlButton;
    private final HBox hBox;

    public ParticipantComponent(Participant participant , ReadOnlyDoubleProperty widthProperty , MeetingParticipantsBarController meetingParticipantsBarController) {
        this.participant = participant;
        this.meetingParticipantsBarController = meetingParticipantsBarController;

        participantIcon = new ImageView(getClass().getResource("/img/participant.png").toExternalForm());
        participantName = new Label(participant.getParticipantName());
        requestControlButton = new Button();
        hBox = new HBox();

        this.getStyleClass().add("participant-component");
        setupLayout(widthProperty);
    }

    public void setupLayout(ReadOnlyDoubleProperty widthProperty) {

        //HBox
        this.prefWidthProperty().bind(widthProperty);
        this.setPrefHeight(COMPONENT_HEIGHT);
        hBox.prefWidthProperty().bind(widthProperty);
        hBox.setPrefHeight(this.getPrefHeight());
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        this.getChildren().add(hBox);

        //Participant Icon
        participantIcon.setFitHeight(COMPONENT_HEIGHT/1.8);
        participantIcon.setFitWidth(COMPONENT_HEIGHT/1.8);
        hBox.getChildren().add(participantIcon);

        //Participant Name
        hBox.getChildren().add(participantName);
        hBox.setPadding(new Insets(0,10,0,10));

        //Spacer
        Pane spacer = new Pane();
        HBox.setHgrow(
                spacer,
                Priority.SOMETIMES
        );
        hBox.getChildren().add(spacer);

        //Raised Hand
        requestControlButton.setGraphic(getButtonIcon("/img/raisedHand.png", COMPONENT_HEIGHT));
        requestControlButton.getStyleClass().add("participant-component-hand-button");
        requestControlButton.setOnAction((event) -> meetingParticipantsBarController.allowRequestControl(participant.getParticipantId()));
        changeRequestStatus(false);
        hBox.getChildren().add(requestControlButton);
    }

    private ImageView getButtonIcon(String path , double prefHeight) {
        ImageView iconImage = new ImageView(new Image(String.valueOf(getClass().getResource(path))));
        iconImage.setFitWidth(prefHeight / 1.5);
        iconImage.setFitHeight(prefHeight / 1.5);
        return iconImage;
    }

    public void changeRequestStatus(boolean status) {
        isControlRequested = status;

        if (isControlRequested) {
            requestControlButton.setOpacity(1);
            requestControlButton.setDisable(false);
        }
        else {
            requestControlButton.setOpacity(0);
            requestControlButton.setDisable(true);
        }
    }

    public Participant getParticipant() {
        return participant;
    }
}
