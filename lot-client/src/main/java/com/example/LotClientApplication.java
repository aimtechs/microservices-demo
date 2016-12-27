package com.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EnableFeignClients
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class LotClientApplication {


  public static void main(String[] args) {
    SpringApplication.run(LotClientApplication.class, args);
  }

  @LoadBalanced
  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

//  @Bean
//  CommandLineRunner testAppContext(ApplicationContext context) {
//    return args -> {
//      Arrays.stream(context.getBeanDefinitionNames())
//              .forEach(System.err::println);
//    };
//  }

  @Bean
  CommandLineRunner testRestTemplate(RestTemplate restTemplate) {
    return args -> {
      final Resources<Lot> lotResources =
              restTemplate.exchange(
                      "http://lot-service/lots/",
                      HttpMethod.GET, null,
                      new ParameterizedTypeReference<Resources<Lot>>() {})
                      .getBody();
      lotResources.forEach(System.out::println);
    };
  }

  @Bean
  CommandLineRunner testLotReader(LotReader lotReader) {
    return args ->
            lotReader.read().getContent()
                    .stream()
                    .forEach(System.out::println);
  }
}

@FeignClient("lot-service")
interface LotReader {
  @RequestMapping(value = "/lots", method = RequestMethod.GET)
  Resources<Lot> read();

  @RequestMapping(value = "/message", method = RequestMethod.GET)
  String readMessage();
}

@RestController
class LotApiGateway {
  private final LotReader lotReader;

  @Autowired
  LotApiGateway(LotReader lotReader) {
    this.lotReader = lotReader;
  }

	@GetMapping("/names")
	Collection<String> names() {
		return lotReader.read()
						.getContent()
            .stream()
            .map(Lot::getLotName)
            .collect(Collectors.toList());
	}

	@GetMapping("/message")
  String message() {
    return lotReader.readMessage();
  }
}

@Getter
@Setter
@ToString
class Lot {
  private Long id;
  private String lotName;
}
