package com.shshon.mypet.image.dto;

import lombok.Builder;

import java.io.InputStream;

@Builder
public record ImageDto(String path,
                       String name,
                       String contentType,
                       Long size,
                       InputStream contents) {

}
