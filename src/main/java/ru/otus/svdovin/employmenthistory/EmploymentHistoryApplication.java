package ru.otus.svdovin.employmenthistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
//@EnableHystrix
public class EmploymentHistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmploymentHistoryApplication.class, args);
    }
}
