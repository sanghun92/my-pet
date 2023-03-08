package com.shshon.mypet.mapper.image;

import com.shshon.mypet.image.dto.ImageDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageDtoMapper {

    public ImageDto toImageMetaData(MultipartFile imageFile) {
        if(imageFile == null) {
            return null;
        }

        try {
            return ImageDto.builder()
                    .name(imageFile.getOriginalFilename())
                    .contentType(imageFile.getContentType())
                    .contents(imageFile.getInputStream())
                    .size(imageFile.getSize())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
