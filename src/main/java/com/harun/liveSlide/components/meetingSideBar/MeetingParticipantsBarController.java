package com.harun.liveSlide.components.meetingSideBar;

import com.harun.liveSlide.model.Participant;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void setParticipants(Set<Participant> participants) {
        VBox vBox = meetingParticipantsBar.getVbox();
        vBox.getChildren().clear();
        for (Participant p : participants) {
            vBox.getChildren().add(new ParticipantComponent(p,meetingParticipantsBar.getScrollPane().widthProperty() , meetingParticipantsBar.getMeetingSideBarController(), p.isRequestingControl()));
        }
    }

    public void addParticipant(Participant participant) {
        VBox vBox = meetingParticipantsBar.getVbox();

        boolean participantExists = vBox.getChildren().stream()
                .filter(element -> element instanceof ParticipantComponent)
                .map(element -> (ParticipantComponent) element)
                .anyMatch(participantComponent -> participantComponent.getParticipant().getUserID().equals(participant.getUserID()));

        if (!participantExists) {
            vBox.getChildren().add(new ParticipantComponent(participant, meetingParticipantsBar.getScrollPane().widthProperty(), meetingParticipantsBar.getMeetingSideBarController(), participant.isRequestingControl()));
        }
    }

    public void removeParticipant(String userID) {
        VBox vBox = meetingParticipantsBar.getVbox();

        List<Node> toRemove = vBox.getChildren()
                .stream()
                .filter(element -> element instanceof ParticipantComponent)
                .map(element -> (ParticipantComponent) element)
                .filter(participantComponent -> participantComponent.getParticipant().getUserID().equals(userID))
                .collect(Collectors.toList());

        vBox.getChildren().removeAll(toRemove);
    }

    public void changeParticipantRequestStatus(String participantId, boolean status) {
        VBox vBox = meetingParticipantsBar.getVbox();
        vBox.getChildren()
                .stream()
                .filter(element -> element instanceof ParticipantComponent)
                .map(element -> (ParticipantComponent) element)
                .filter(participantComponent -> participantComponent.getParticipant().getUserID().equals(participantId))
                .forEach(participantComponent -> participantComponent.changeRequestStatus(status));
    }

    public void allowRequestControl(String participationId) {
        meetingParticipantsBar.getMainScreen().networkMainManager.changePresenter(participationId);
    }

}
