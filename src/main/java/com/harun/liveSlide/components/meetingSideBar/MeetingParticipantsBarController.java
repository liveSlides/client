package com.harun.liveSlide.components.meetingSideBar;

import com.harun.liveSlide.model.Participant;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

public class MeetingParticipantsBarController {
    private double sideBarWidth = 325;
    private boolean isShown = false;
    private MeetingParticipantsBar meetingParticipantsBar;

    public MeetingParticipantsBarController(MeetingParticipantsBar meetingParticipantsBar) {
        this.meetingParticipantsBar = meetingParticipantsBar;
    }

    public double getSideBarWidth() {
        return sideBarWidth;
    }

    public boolean isShown() {
        return isShown;
    }

    public void show() {
        meetingParticipantsBar.setPrefWidth(sideBarWidth);
        meetingParticipantsBar.setMaxWidth(sideBarWidth);
        isShown = true;
    }

    public void hide() {
        meetingParticipantsBar.setPrefWidth(0);
        meetingParticipantsBar.setMaxWidth(0);
        isShown = false;
    }

    public void setParticipants(Participant[] participants) {
        VBox vBox = meetingParticipantsBar.getVbox();
        vBox.getChildren().clear();
        for (Participant p : participants) {
            vBox.getChildren().add(new ParticipantComponent(p,meetingParticipantsBar.getScrollPane().widthProperty() , meetingParticipantsBar.getMeetingSideBarController()));
        }
    }

    public void addParticipant(Participant participant) {
        VBox vBox = meetingParticipantsBar.getVbox();
        vBox.getChildren().add(new ParticipantComponent(participant,meetingParticipantsBar.getScrollPane().widthProperty() , meetingParticipantsBar.getMeetingSideBarController()));
    }

    public void removeParticipant(Participant participant) {
        VBox vBox = meetingParticipantsBar.getVbox();
        vBox.getChildren()
                .stream()
                .filter(element -> element instanceof  ParticipantComponent)
                .map(element -> (ParticipantComponent) element)
                .filter(participantComponent -> participantComponent.getParticipant().getParticipantId() == participant.getParticipantId())
                .forEach(participantComponent -> {vBox.getChildren().remove(participantComponent);});
    }

    public void changeParticipantRequestStatus(int participantId, boolean status) {
        VBox vBox = meetingParticipantsBar.getVbox();
        vBox.getChildren()
                .stream()
                .filter(element -> element instanceof ParticipantComponent)
                .map(element -> (ParticipantComponent) element)
                .filter(participantComponent -> participantComponent.getParticipant().getParticipantId() == participantId)
                .forEach(participantComponent -> participantComponent.changeRequestStatus(status));
    }

    public void allowRequestControl(int participationId) {
    }

}