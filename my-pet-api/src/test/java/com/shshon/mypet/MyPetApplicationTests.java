package com.shshon.mypet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class MyPetApplicationTests {

    @Test
    void contextLoads() {
    }

}
