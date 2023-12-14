package com.harun.liveSlide.model.network.pdfFile;

import com.harun.liveSlide.model.network.ResponseStatus;

public class UploadPDFResponse {
    public ResponseStatus status;

    public UploadPDFResponse() {

    }

    public UploadPDFResponse(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
