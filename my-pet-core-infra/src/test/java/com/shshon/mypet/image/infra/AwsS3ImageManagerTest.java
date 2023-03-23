package com.shshon.mypet.image.infra;

import com.shshon.mypet.config.AwsTestConfig;
import com.shshon.mypet.image.domain.ImageManager;
import com.shshon.mypet.image.dto.ImageDto;
import com.shshon.mypet.properties.AwsProperties;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@EnableConfigurationProperties(AwsProperties.class)
@ContextConfiguration(
        initializers = ConfigDataApplicationContextInitializer.class,
        classes = {AwsTestConfig.class, AwsS3ImageManager.class}
)
@TestPropertySource(properties = {
        "spring.main.allow-bean-definition-overriding=true",
})
class AwsS3ImageManagerTest {

    @Autowired
    private ImageManager imageManager;

    @AfterAll
    static void tearDown(@Autowired S3Mock s3Mock) {
        s3Mock.stop();
    }

    @Test
    @DisplayName("AWS S3 이미지 업로드에 성공한다")
    void awsS3ImageUploadTest() {
        // given
        String fileName = "test.png";
        String contentType = "image/png";

        ImageDto imageDto = ImageDto.builder()
                .name(fileName)
                .contentType(contentType)
                .contents(InputStream.nullInputStream())
                .size(0L)
                .build();

        // when
        String s3Path = imageManager.upload(imageDto);

        // then
        assertThat(s3Path).isNotEmpty();
    }
}
