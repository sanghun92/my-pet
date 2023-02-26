package com.shshon.mypet.image.dto;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import lombok.Builder;

import java.io.InputStream;

@Builder
public record ImageDto(String path,
                       String name,
                       String contentType,
                       Long size,
                       InputStream contents) {
    /*public static ImageDto of(ImageMetaData imageMetaData, S3ObjectInputStream objectContent) {
        return ImageDto.builder()
                .id(imageMetaData.getId())
                .path(builder().path)
                .name(imageMetaData.getName())
                .contentType(imageMetaData.getContentType())
                .size(imageMetaData.getSize())
                .contents(objectContent)
                .build();
    }*/

    public static ImageDto of(S3Object s3Object) {
        ObjectMetadata objectMetadata = s3Object.getObjectMetadata();
        return ImageDto.builder()
                .path(s3Object.getKey())
                .contentType(objectMetadata.getContentType())
                .size(objectMetadata.getContentLength())
                .contents(s3Object.getObjectContent())
                .build();
    }
}
