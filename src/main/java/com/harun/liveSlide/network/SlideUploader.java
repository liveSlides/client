package com.harun.liveSlide.network;

import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.network.pdfFile.UploadPartOfPDFResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

public class SlideUploader {
    private String filePath;
    private int chunkSize = 1024;
    private FileInputStream fileInputStream;
    private File file;

    public SlideUploader() {
        StompClient.subscribeRaw(
                "/topic/uploadPartOfPDF/"
                        + GlobalVariables.SESSION_ID + "/" + GlobalVariables.USER_ID,
                UploadPartOfPDFResponse.class, response -> {
                    handleUploadPartOfPDFResponse(((UploadPartOfPDFResponse) response));
                });
    }

    private void sendBase64Chunk(String base64Chunk, String fileName, double fileSize) {
        StompClient.sendMessage(
                "/app/uploadPDF/" +
                        GlobalVariables.SESSION_ID +
                        "/" +
                        GlobalVariables.USER_ID +
                        "/" +
                        fileName +
                        "/" +
                        fileSize, base64Chunk);
    }

    public void startToUploadSlide() {
        file = new File(filePath);

        try {
            fileInputStream = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        uploadSlide();
    }

    private void uploadSlide() {
        String fileName = file.getName();
        double fileSize = file.length();

        byte[] buffer = new byte[chunkSize];
        int bytesRead;

        try {
            bytesRead = fileInputStream.read(buffer);

            if (bytesRead == -1)
                return;

            // If bytesRead is less than chunkSize, resize the buffer
            byte[] actualBytesRead = bytesRead < chunkSize ? Arrays.copyOf(buffer, bytesRead) : buffer;

            // Encode the chunk as Base64 before sending
            String base64Chunk = Base64.getEncoder().encodeToString(actualBytesRead);
            sendBase64Chunk(base64Chunk, fileName, fileSize);

        }catch (Exception e) {
            System.out.println("Dosya okunamadı");
        }
    }

    private void handleUploadPartOfPDFResponse(UploadPartOfPDFResponse response) {
        uploadSlide();
    }

    public String getFilePath() {
        return filePath;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

}
