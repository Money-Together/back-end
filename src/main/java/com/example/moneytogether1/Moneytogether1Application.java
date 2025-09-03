package com.example.moneytogether1;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Moneytogether1Application {

    public static void main(String[] args) {

        // .env 로드
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        SpringApplication.run(Moneytogether1Application.class, args);
    }
}
