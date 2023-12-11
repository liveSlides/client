package com.harun.liveSlide;

import com.harun.liveSlide.enums.UserType;

public class AuthLayoutController {
    private UserType userType;
    private final MainWindow mainWindow;

    public AuthLayoutController(UserType userType , MainWindow mainWindow) {
        this.userType = userType;
        this.mainWindow = mainWindow;
        updateLayoutAccordingToUserType();
    }

    public void updateLayoutAccordingToUserType() {
        switch (userType) {
            case HOST_PRESENTER:
            case PARTICIPANT_PRESENTER:
                mainWindow.pdfViewer.setToolsVisible(true);
                mainWindow.topSide.setVisibleControlBackButton(false);
                break;
            case HOST_SPECTATOR:
                mainWindow.pdfViewer.setToolsVisible(false);
                mainWindow.topSide.setVisibleControlBackButton(true);
                break;
            case PARTICIPANT_SPECTATOR:
                mainWindow.pdfViewer.setToolsVisible(false);
                mainWindow.topSide.setVisibleControlBackButton(false);
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
