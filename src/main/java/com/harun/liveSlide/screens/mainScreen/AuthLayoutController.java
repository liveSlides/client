package com.harun.liveSlide.screens.mainScreen;

import com.harun.liveSlide.enums.UserType;

public class AuthLayoutController {
    private UserType userType;
    private final MainScreen mainScreen;

    public AuthLayoutController(UserType userType , MainScreen mainScreen) {
        this.userType = userType;
        this.mainScreen = mainScreen;
        updateLayoutAccordingToUserType();
    }

    public void updateLayoutAccordingToUserType() {
        switch (userType) {
            case HOST_PRESENTER:
            case PARTICIPANT_PRESENTER:
                mainScreen.pdfViewer.setToolsVisible(true);
                mainScreen.topSide.setVisibleControlBackButton(false);
                break;
            case HOST_SPECTATOR:
                mainScreen.pdfViewer.setToolsVisible(false);
                mainScreen.topSide.setVisibleControlBackButton(true);
                break;
            case PARTICIPANT_SPECTATOR:
                mainScreen.pdfViewer.setToolsVisible(false);
                mainScreen.topSide.setVisibleControlBackButton(false);
                break;
        }
    }

    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
