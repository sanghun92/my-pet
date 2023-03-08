package com.shshon.mypet.image.application;

import com.shshon.mypet.image.domain.ImageManager;
import com.shshon.mypet.image.domain.ImageMetaData;
import com.shshon.mypet.image.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final ImageManager imageManager;

    public ImageMetaData upload(ImageDto imageDto) {
        String imageKey = imageManager.upload(imageDto);
        return ImageMetaData.builder()
                .path(imageKey)
                .name(imageDto.name())
                .contentType(imageDto.contentType())
                .size(imageDto.size())
                .build();
    }

    public ImageDto findByImageKey(ImageMetaData imageMetaData) {
        return imageManager.retrieve(imageMetaData.getPath().toString());
    }
}
