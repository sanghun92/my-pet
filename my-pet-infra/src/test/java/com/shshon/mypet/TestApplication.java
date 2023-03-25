package com.shshon.mypet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ConfigurationPropertiesScan
@ActiveProfiles(profiles = "test")
public class TestApplication {

    @Test
    void contextLoads() {
    }
}
