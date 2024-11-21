package com.nexign.task;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.boot.SpringApplication.run;

@EnableScheduling
@SpringBootApplication
public class TaskApplication {

    public static void main(String[] args) {
        run(TaskApplication.class, args);
    }
}
