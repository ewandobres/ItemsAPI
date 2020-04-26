package com.reclaimium.itemsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class ItemsapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemsapiApplication.class, args);
    }

}
