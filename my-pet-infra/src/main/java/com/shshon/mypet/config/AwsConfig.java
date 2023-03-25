package com.shshon.mypet.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.shshon.mypet.properties.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    private final AwsProperties awsProperties;

    @Bean
    public AmazonS3 amazonS3() {
        AwsProperties.Credentials credentials = awsProperties.credentials();
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                credentials.accessKey(),
                credentials.secretKey()
        );
        return AmazonS3ClientBuilder.standard()
                .withRegion(awsProperties.s3().region())
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
