package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
// import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class DemoClient {

  public static void main(String[] args) {
    SpringApplication.run(DemoClient.class, args);
  }

}

@Component
class Main implements ApplicationRunner {

  @Autowired
  WebClient webClient() {
    //return WebClient.builder().filter(ExchangeFilterFunctions.basicAuthentication("admin", "password")).build();
    return WebClient.builder().build();
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    String response = webClient().get().uri("http://localhost:8080/api/customers").retrieve().bodyToMono(String.class).block();
    System.out.println("Response: " + response);
  }

}