package com.shshon.mypet.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws")
public record AwsProperties(
        Credentials credentials,
        AwsS3 s3,
        Stack stack
) {
    public record Credentials(String accessKey,
                               String secretKey) { }
    public record AwsS3(String bucket,
                         String region) { }

    public record Stack(Boolean auto) {}
}
