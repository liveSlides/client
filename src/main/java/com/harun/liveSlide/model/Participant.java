package com.harun.liveSlide.model;

public class Participant {
    private final int participantId;
    private final String participantName;

    public Participant(int participantId, String participantName) {
        this.participantId = participantId;
        this.participantName = participantName;
    }

    public int getParticipantId() {
        return participantId;
    }

    public String getParticipantName() {
        return participantName;
    }
}
