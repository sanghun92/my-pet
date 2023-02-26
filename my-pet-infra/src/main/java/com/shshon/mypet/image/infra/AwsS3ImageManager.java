package com.shshon.mypet.image.infra;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.shshon.mypet.image.domain.ImageManager;
import com.shshon.mypet.image.dto.ImageDto;
import com.shshon.mypet.properties.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class AwsS3ImageManager implements ImageManager {

    private final AwsProperties awsProperties;
    private final AmazonS3 amazonS3;

    @Override
    public ImageDto retrieve(String path) {
        S3Object object = amazonS3.getObject(awsProperties.s3().bucket(), path);
        return ImageDto.of(object);
    }

    @Override
    public void remove(String path) {
        amazonS3.deleteObject(awsProperties.s3().bucket(), path);
    }

    @Override
    public String upload(ImageDto imageDto) {
        return uploadToS3(imageDto);
    }

    private String uploadToS3(ImageDto imageDto) {
        InputStream contents = imageDto.contents();
        ObjectMetadata objectMetadata = new ObjectMetadata();

        try {
            objectMetadata.setContentType(imageDto.contentType());
            objectMetadata.setContentLength(contents.available());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PutObjectRequest putObjectRequest = new PutObjectRequest(
                awsProperties.s3().bucket(),
                UUID.randomUUID().toString(),
                contents,
                objectMetadata
        );

        try {
            amazonS3.putObject(putObjectRequest);
            return putObjectRequest.getKey();
        } catch (SdkClientException e) {
            log.error("Fail to update to s3.", e);
            throw e;
        }
    }
}
