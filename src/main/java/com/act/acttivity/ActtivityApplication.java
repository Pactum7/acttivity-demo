package com.act.acttivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ActtivityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActtivityApplication.class, args);
    }

}
