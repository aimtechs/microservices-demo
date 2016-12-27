package com.example.ribbontest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zhwan on 16. 12. 27.
 */
@SpringBootApplication
@RestController
@RibbonClient(name="simpleApp")
public class RibbonApp {
  public static void main(String[] args) {
    SpringApplication.run(RibbonApp.class, args);
  }

  @LoadBalanced
  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @GetMapping("/test/{name}")
  String test(@PathVariable String name) {
    return restTemplate().getForObject("http://simpleApp/hello/" + name, String.class);
  }
}


