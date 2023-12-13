package com.harun.liveSlide.model.network.participantList;

import com.harun.liveSlide.model.network.ResponseStatus;

public class DisconnectResponse {
    public ResponseStatus status;

    public DisconnectResponse() {

    }

    public DisconnectResponse(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
