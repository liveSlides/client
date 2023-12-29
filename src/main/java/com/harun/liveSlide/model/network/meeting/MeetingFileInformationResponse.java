package com.harun.liveSlide.model.network.meeting;

import com.harun.liveSlide.components.pdfViewer.PDFTool;
import com.harun.liveSlide.components.pdfViewer.PenColor;
import com.harun.liveSlide.components.pdfViewer.PenEraserSize;

import java.util.Objects;

public class MeetingFileInformationResponse {
    private String fileName;

    public MeetingFileInformationResponse() {

    }

    public MeetingFileInformationResponse(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingFileInformationResponse that = (MeetingFileInformationResponse) o;
        return Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }

    @Override
    public String toString() {
        return "MeetingFileInformationResponse{" +
                "fileName='" + fileName + '\'' +
                '}';
    }
}

