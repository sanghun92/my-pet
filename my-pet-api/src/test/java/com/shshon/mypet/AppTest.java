package com.shshon.mypet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles(profiles = "test")
public class AppTest {

    @Test
    void contextLoads() {
    }
}
