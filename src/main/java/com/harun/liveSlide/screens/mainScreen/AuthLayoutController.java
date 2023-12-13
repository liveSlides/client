package com.harun.liveSlide.screens.mainScreen;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;

public class AuthLayoutController {
    private final MainScreen mainScreen;

    public AuthLayoutController(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        updateLayoutAccordingToUserType();
    }

    public void updateLayoutAccordingToUserType() {

        switch (GlobalVariables.userType) {
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

}
