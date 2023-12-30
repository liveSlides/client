package com.harun.liveSlide.components.meetingTopBar;

import com.harun.liveSlide.screens.mainScreen.MainScreen;
import javafx.event.ActionEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

public class MeetingTopBarController {
    private final MainScreen mainScreen;
    private boolean isControlRequesting = false;
    private MeetingTopBar meetingTopBar;
    public MeetingTopBarController(MeetingTopBar meetingTopBar , MainScreen mainScreen) {
        this.meetingTopBar = meetingTopBar;
        this.mainScreen = mainScreen;
    }

    public void takeControlBack() {

    }

    public void changeRequestControlStatus(boolean status) {
        isControlRequesting = status;

        if (isControlRequesting){
            meetingTopBar.changeHandIcon("/img/raisedHand.png");
        }
        else {
            meetingTopBar.changeHandIcon("/img/hand.png");
        }
    }

    public void showHideParticipants() {
        meetingTopBar.getMainWindow().showHideParticipantsTab();
    }

    public void takeControlBack(ActionEvent actionEvent) {
        takeControlBack();
    }

    public void requestControl(ActionEvent actionEvent) {
        changeRequestControlStatus(!isControlRequesting);
        mainScreen.networkMainManager.requestControl(isControlRequesting);
    }

    public void showParticipants(ActionEvent actionEvent) {
        showHideParticipants();
    }
}
