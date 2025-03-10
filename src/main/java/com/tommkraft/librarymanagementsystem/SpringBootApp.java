package com.tommkraft.librarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "com.tommkraft.librarymanagementsystem.entity"
})
@EnableJpaRepositories(basePackages = {
        "com.tommkraft.librarymanagementsystem.repository.jpa"
})
@EnableMongoRepositories(basePackages = {
        "com.tommkraft.librarymanagementsystem.repository.mongodb"
})

public class SpringBootApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }
}