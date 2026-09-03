package org.tasktracker.tasktrackerauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaskTrackerAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerAuthServiceApplication.class, args);
    }

}
