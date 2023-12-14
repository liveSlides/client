package com.harun.liveSlide.network;

import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.network.pdfFile.DownloadPDFRequest;
import com.harun.liveSlide.model.network.pdfFile.DownloadPartOfPDFResponse;
import com.harun.liveSlide.model.network.pdfFile.UploadPartOfPDFResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

public class SlideDownloader {
    public File pdfFile;

    public SlideDownloader() {
        StompClient.subscribeRaw(
                "/topic/downloadPartOfPDF/"
                        + GlobalVariables.SESSION_ID + "/" + GlobalVariables.USER_ID,
                DownloadPartOfPDFResponse.class, response -> {
                    handleDownloadPartOfPDFResponse(((DownloadPartOfPDFResponse) response));
                });
    }


    public void downloadSlide() {
        long gatheredSize = pdfFile == null ? 0 : pdfFile.length();

        DownloadPDFRequest request = new DownloadPDFRequest(GlobalVariables.SESSION_ID, GlobalVariables.USER_ID , gatheredSize);
        StompClient.sendMessage("/app/downloadPDF", request);
    }

    private void writeFile(DownloadPartOfPDFResponse response) {
        // Get Folder
        String folderPath = "src/meetingSlides";
        File folder = new File(folderPath);

        // Create the parent directories if they don't exist
        if (!folder.exists() && !folder.mkdirs()) {
            throw new RuntimeException("Failed to create directory: " + folderPath);
        }

        // Get File
        String filePath = folderPath + File.separator + response.getFileName();
        File file = new File(filePath);

        // Create the file if it doesn't exist
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Failed to create file: " + filePath);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error creating file: " + filePath, e);
            }
        }

        // Decode Base64 data
        byte[] chunk = Base64.getDecoder().decode(response.content);

        // Append the bytes to the file
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write(chunk);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filePath, e);
        }

        pdfFile = file;
    }

    private void handleDownloadPartOfPDFResponse(DownloadPartOfPDFResponse response) {
        System.out.println(response);
        writeFile(response);
        downloadSlide();
    }

}

