package com.harun.liveSlide.components.meetingSideBar;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ParticipantComponent extends Pane {
    private final String username;
    private final HBox hBox;
    public ParticipantComponent(String username , double width) {
        this.username = username;
        this.hBox = new HBox();
        this.setPrefWidth(width);
        this.setPrefHeight(40);
        setupLayout();
    }

    public void setupLayout() {
        hBox.setPrefWidth(this.getPrefWidth());
        hBox.setPrefHeight(this.getPrefHeight());
        hBox.setBackground(new Background(new BackgroundFill(Color.PINK,null,null)));
        this.setBackground(new Background(new BackgroundFill(Color.BLUE,null,null)));
        this.getChildren().add(hBox);

        hBox.getChildren().add(new Label(username));
    }

}
