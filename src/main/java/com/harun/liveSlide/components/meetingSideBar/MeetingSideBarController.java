package com.harun.liveSlide.components.meetingSideBar;

public class MeetingSideBarController {
    private double sideBarWidth = 325;
    private boolean isShown = false;
    private MeetingSideBar meetingSideBar;

    public MeetingSideBarController(MeetingSideBar meetingSideBar) {
        this.meetingSideBar = meetingSideBar;
    }

    public double getSideBarWidth() {
        return sideBarWidth;
    }

    public boolean isShown() {
        return isShown;
    }

    public void show() {
        meetingSideBar.setPrefWidth(sideBarWidth);
        meetingSideBar.setMaxWidth(sideBarWidth);
        isShown = true;
    }

    public void hide() {
        meetingSideBar.setPrefWidth(0);
        meetingSideBar.setMaxWidth(0);
        isShown = false;
    }
}
