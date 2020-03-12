package org.geeksword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.locks.LockSupport;


@SpringBootApplication
public class Application {

    public static void main(String[] args){
        SpringApplication springApplication = new SpringApplication(Application.class);
        WebApplicationType webApplicationType = springApplication.getWebApplicationType();
        ConfigurableApplicationContext context = springApplication.run(args);
        if(webApplicationType == WebApplicationType.NONE){
            LockSupport.park();
        }
        context.stop();
    }
}
