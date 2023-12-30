package com.harun.liveSlide.screens.mainScreen;

import com.harun.liveSlide.enums.UserType;
import com.harun.liveSlide.global.GlobalVariables;
import javafx.event.Event;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

import javax.xml.stream.EventFilter;

public class AuthLayoutController {
    private final MainScreen mainScreen;

    public AuthLayoutController(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        updateLayoutAccordingToUserType();
    }

    public void updateLayoutAccordingToUserType() {

        switch (GlobalVariables.userType) {
            case HOST_PRESENTER:
                mainScreen.pdfViewer.viewArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                mainScreen.pdfViewer.viewArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                mainScreen.pdfViewer.setActiveZoomingGesture(true);
                mainScreen.pdfViewer.setActiveScrollingGesture(true);

                mainScreen.pdfViewer.setToolsVisible(true);
                mainScreen.topSide.setVisibleControlBackButton(false);
                break;
            case PARTICIPANT_PRESENTER:
                mainScreen.pdfViewer.viewArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                mainScreen.pdfViewer.viewArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                mainScreen.pdfViewer.setActiveZoomingGesture(true);
                mainScreen.pdfViewer.setActiveScrollingGesture(true);

                mainScreen.pdfViewer.setToolsVisible(true);
                mainScreen.pdfViewer.setFileUploadToolVisible(false);
                mainScreen.topSide.setVisibleControlBackButton(false);
                mainScreen.topSide.setVisibleRequestControlButton(false);
                break;
            case HOST_SPECTATOR:
                mainScreen.pdfViewer.viewArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                mainScreen.pdfViewer.viewArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                mainScreen.pdfViewer.setActiveZoomingGesture(false);
                mainScreen.pdfViewer.setActiveScrollingGesture(false);

                mainScreen.pdfViewer.setToolsVisible(false);
                mainScreen.topSide.setVisibleControlBackButton(true);
                break;
            case PARTICIPANT_SPECTATOR:
                mainScreen.pdfViewer.viewArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                mainScreen.pdfViewer.viewArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                mainScreen.pdfViewer.setActiveZoomingGesture(false);
                mainScreen.pdfViewer.setActiveScrollingGesture(false);

                mainScreen.pdfViewer.setToolsVisible(false);
                mainScreen.pdfViewer.setFileUploadToolVisible(false);
                mainScreen.topSide.setVisibleControlBackButton(false);
                mainScreen.topSide.setVisibleRequestControlButton(true);
                break;
        }
    }

}
