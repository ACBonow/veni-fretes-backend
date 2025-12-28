package com.venifretes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VeniFretesApplication {

    public static void main(String[] args) {
        SpringApplication.run(VeniFretesApplication.class, args);
    }

}
