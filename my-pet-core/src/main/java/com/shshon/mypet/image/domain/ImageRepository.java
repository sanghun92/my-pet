package com.shshon.mypet.image.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageMetaData, Long> {
}
