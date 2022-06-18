package com.zephyr.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.zephyr")
public class BackUaaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackUaaApplication.class, args);
    }
}
