package com.harun.liveSlide.components.meetingSideBar;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class MeetingSideBar extends Pane {

    private final MeetingSideBarController meetingSideBarController;

    public MeetingSideBar() {
        this.meetingSideBarController = new MeetingSideBarController(this);
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
