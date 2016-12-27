package com.example.simpleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 이 테스트를 하기 위해서는 아래의 dependency를 제거 해야 함.
 *
 * 		<dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-starter-config</artifactId>
 </dependency>
 <dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-starter-eureka</artifactId>
 </dependency>
 <dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-starter-feign</artifactId>
 </dependency>
 <dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-starter-zuul</artifactId>
 </dependency>
 *
 * Created by zhwan on 16. 12. 27.
 */
@SpringBootApplication
@RestController
public class SimpleServiceApp {

  public static void main(String[] args) {
    SpringApplication.run(SimpleServiceApp.class, args);
  }

  @GetMapping("/hello/{name}")
  String hello(@PathVariable String name) {
    System.err.println("name is " + name);
    return "Hello " + name;
  }
}
