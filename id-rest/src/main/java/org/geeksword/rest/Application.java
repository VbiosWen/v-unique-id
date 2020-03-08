package org.geeksword.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.geeksword")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
