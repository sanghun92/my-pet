package com.shshon.mypet;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MyPetApplication implements CoreApplication {

    public static void main(String[] args) {
        new MyPetApplication().start(args);
    }

    @Override
    public void start(String[] args) {
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(MyPetApplication.class);
        springApplicationBuilder.run(args);
    }
}
