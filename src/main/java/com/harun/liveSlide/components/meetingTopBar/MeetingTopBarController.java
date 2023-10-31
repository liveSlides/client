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

    public void leave(ActionEvent actionEvent) {
        leave();
    }
}
