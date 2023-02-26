package com.shshon.mypet.image.domain;

import com.shshon.mypet.image.dto.ImageDto;

public interface ImageManager {

    ImageDto retrieve(String path);

    void remove(String path);

    String upload(ImageDto imageDto);
}
