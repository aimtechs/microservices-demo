package com.example;

import com.netflix.discovery.converters.Auto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@ComponentScan({"com.example", "com.example2"})
@EnableDiscoveryClient // eureka client 등록
@SpringBootApplication
public class LotServiceApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(LotServiceApplication.class, args);
  }

  @Autowired
  LotRepository lotRepository;

  @Autowired
  ApplicationContext context;

  @Override
  public void run(String... args) throws Exception {
    Stream.of("Lot-A", "Lot-B", "Lot-C", "helloWorld")
            .forEach(lot -> lotRepository.save(new Lot(lot)));
    lotRepository.findAll().forEach(System.out::println);

    Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
  }
}

//@Component
//class SampleData implements CommandLineRunner{
//
//  private final LotRepository lotRepository;
//
//  @Autowired
//  SampleData(LotRepository lotRepository) {
//    this.lotRepository = lotRepository;
//  }
//
//  @Override
//  public void run(String... strings) throws Exception {
//    Stream.of("Lot-A", "Lot-B", "Lot-C")
//            .forEach(lot -> lotRepository.save(new Lot(lot)));
//    lotRepository.findAll().forEach(System.out::println);
//  }
//}

@RestController
@RefreshScope
@Slf4j
class MessageRestController {
  private final String message;

  public MessageRestController(@Value("${message}") String message) {
    this.message = message;
  }

  @GetMapping("/message")
  String message() {
    log.info("i'm called. {}", this.message);
    return this.message;
  }
}

@RepositoryRestResource
interface LotRepository extends JpaRepository<Lot, Long> {
  List<Lot> findByLotName(@Param("name") String name);
}

@Entity
@Getter
class Lot {
  @Id
  @GeneratedValue
  private Long id;

  private String lotName;

  Lot() {
  }

  public Lot(String lotName) {
    this.lotName = lotName;
  }

  @Override
  public String toString() {
    return "Lot{" +
            "id=" + id +
            ", lotName='" + lotName + '\'' +
            '}';
  }

  public String modifyLotName() {
    return this.lotName += "_aaa";
  }
}
