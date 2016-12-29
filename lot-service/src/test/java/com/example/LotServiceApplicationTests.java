package com.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.springframework.boot.test.context.SpringBootTest.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class LotServiceApplicationTests {

  @LocalServerPort
  int port;

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  public void test() {

    System.err.println("port is " + port);
    System.err.println("port is " + port);
    System.err.println("port is " + port);
    System.err.println("port is " + port);
    System.err.println("port is " + port);

//    final PagedResources<Lot> resources =
//            restTemplate().exchange(
//                    "http://localhost:8080/lots",
//                    HttpMethod.GET, null,
//                    new ParameterizedTypeReference<PagedResources<Lot>>() {
//    }).getBody();
//    System.out.println(resources);
//    System.out.println(resources.getContent());


    System.out.println("========================================");

    final Resources<Lot> lots =
            this.restTemplate.exchange(
                    "/lots",
//                    "http://localhost:{port}/lots",
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<Resources<Lot>>() {},
                    this.port)
                    .getBody();

    lots.getContent().stream().forEach(System.err::println);
  }

  @Ignore
  @Test
  public void contextLoads() {
    final Lot lot = restTemplate.getForObject("http://localhost:8080/lots/1", Lot.class);
    System.out.println(lot);
  }

}
