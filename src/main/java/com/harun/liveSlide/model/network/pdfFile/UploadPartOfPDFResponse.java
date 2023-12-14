package com.harun.liveSlide.model.network.pdfFile;

import com.harun.liveSlide.model.network.ResponseStatus;

public class UploadPartOfPDFResponse {
    public ResponseStatus status;

    public UploadPartOfPDFResponse() {

    }

    public UploadPartOfPDFResponse(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
