package com.harun.liveSlide.network;

import com.harun.liveSlide.global.GlobalVariables;
import com.harun.liveSlide.model.network.meeting.MeetingInitialInformationResponse;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class S3Manager {

    private final S3Client s3;
    private final String bucketName;
    private final ExecutorService executorService;
    private final NetworkMainManager networkMainManager;

    public S3Manager(NetworkMainManager networkMainManager) {
        this.networkMainManager = networkMainManager;

        Properties props = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.bucketName = props.getProperty("aws.bucket-name");
        this.s3 = S3Client.builder()
                .region(Region.of(props.getProperty("aws.region")))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                props.getProperty("aws.access-key-id"),
                                props.getProperty("aws.secret-access-key"))))
                .build();
        this.executorService = Executors.newFixedThreadPool(2); // Adjust the number of threads as needed
    }

    public void uploadFile(String fileName, String filePath) {
        executorService.submit(() -> {
            String s3Key = GlobalVariables.SESSION_ID + "/" + fileName;
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            s3.putObject(putObjectRequest, Paths.get(filePath));
            networkMainManager.loadPDFWithNotify(fileName,filePath);
        });
    }

    public void downloadFile(String fileName, String downloadPath) {
        executorService.submit(() -> {
            String s3Key = GlobalVariables.SESSION_ID + "/" + fileName;
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            try (ResponseInputStream<GetObjectResponse> s3Object = s3.getObject(getObjectRequest);
                 FileOutputStream fos = new FileOutputStream(new File(downloadPath + "/" + fileName))) {
                byte[] read_buf = new byte[1024];
                int read_len;
                while ((read_len = s3Object.read(read_buf)) > 0) {
                    fos.write(read_buf, 0, read_len);
                }
                networkMainManager.loadPDF(downloadPath + "/" + fileName , null);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    public void downloadFile(String fileName, String downloadPath , MeetingInitialInformationResponse response) {
        executorService.submit(() -> {
            String s3Key = GlobalVariables.SESSION_ID + "/" + fileName;
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            try (ResponseInputStream<GetObjectResponse> s3Object = s3.getObject(getObjectRequest);
                 FileOutputStream fos = new FileOutputStream(new File(downloadPath + "/" + fileName))) {
                byte[] read_buf = new byte[1024];
                int read_len;
                while ((read_len = s3Object.read(read_buf)) > 0) {
                    fos.write(read_buf, 0, read_len);
                }
                networkMainManager.loadPDF(downloadPath + "/" + fileName , response);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
