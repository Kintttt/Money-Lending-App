package com.moneylendingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MoneyLendingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyLendingAppApplication.class, args);
    }

}
