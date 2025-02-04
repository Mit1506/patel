package com.example.cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.cafe.repositery")
public class Cafemanagement1Application {
    public static void main(String[] args) {
        SpringApplication.run(Cafemanagement1Application.class, args);
    }
}
