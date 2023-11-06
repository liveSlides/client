package com.harun.liveSlide.components.meetingTopBar;

import javafx.event.ActionEvent;

public class MeetingTopBarController {
    private MeetingTopBar meetingTopBar;
    public MeetingTopBarController(MeetingTopBar meetingTopBar) {
        this.meetingTopBar = meetingTopBar;
    }

    public void leave() {
        meetingTopBar.getStage().close();
    }

    public void takeControlBack() {

    }

    public void requestControl() {

    }

    public void leave(ActionEvent actionEvent) {
        leave();
    }

    public void takeControlBack(ActionEvent actionEvent) {
        takeControlBack();
    }

    public void requestControl(ActionEvent actionEvent) {
        requestControl();
    }
}
