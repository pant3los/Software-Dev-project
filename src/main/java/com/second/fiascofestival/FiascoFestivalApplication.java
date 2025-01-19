package com.second.fiascofestival;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.second.fiascofestival")
public class FiascoFestivalApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiascoFestivalApplication.class, args);
    }

}
