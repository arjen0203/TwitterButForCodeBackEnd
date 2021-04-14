package com.arjen0203.codex.service.exampleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/** The example application. */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ExampleServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(ExampleServiceApplication.class, args);
  }
}
