package com.harun.liveSlide.components.meetingSideBar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ParticipantComponent extends Pane {
    private final double COMPONENT_HEIGHT = 40;
    private final String username;
    private final ImageView participantIcon;
    private final Label participantName;
    private final Button raisedHand;
    private final HBox hBox;
    public ParticipantComponent(String username , double width) {
        this.username = username;

        participantIcon = new ImageView(getClass().getResource("/img/participant.png").toExternalForm());
        participantName = new Label(username);
        raisedHand = new Button();
        hBox = new HBox();

        this.getStyleClass().add("participant-component");
        setupLayout(width);
    }

    public void setupLayout(double width) {
        this.setPrefWidth(width);
        this.setPrefHeight(COMPONENT_HEIGHT);

        hBox.setPrefWidth(this.getPrefWidth());
        hBox.setPrefHeight(this.getPrefHeight());
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        this.getChildren().add(hBox);

        participantIcon.setFitHeight(COMPONENT_HEIGHT/1.8);
        participantIcon.setFitWidth(COMPONENT_HEIGHT/1.8);
        hBox.getChildren().add(participantIcon);

        hBox.getChildren().add(participantName);

        Pane spacer = new Pane();
        HBox.setHgrow(
                spacer,
                Priority.SOMETIMES
        );
        hBox.getChildren().add(spacer);

        raisedHand.setGraphic(getButtonIcon("/img/raisedHand.png", COMPONENT_HEIGHT));
        raisedHand.getStyleClass().add("participant-component-hand-button");
        hBox.getChildren().add(raisedHand);
    }

    private ImageView getButtonIcon(String path , double prefHeight) {
        ImageView iconImage = new ImageView(new Image(String.valueOf(getClass().getResource(path))));
        iconImage.setFitWidth(prefHeight / 1.5);
        iconImage.setFitHeight(prefHeight / 1.5);
        return iconImage;
    }

}
