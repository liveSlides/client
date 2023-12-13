package com.harun.liveSlide.model.network.participantList;

import com.harun.liveSlide.model.Participant;

import java.util.Set;

public class SessionParticipantsResponse {
    public String sessionId;
    public Set<Participant> participants;

    public SessionParticipantsResponse() {

    }

    public SessionParticipantsResponse(String sessionId , Set<Participant> participants) {
        this.sessionId = sessionId;
        this.participants = participants;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }
}
