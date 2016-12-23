package com.example;

import com.netflix.discovery.converters.Auto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import java.util.stream.Stream;


@SpringBootApplication
public class LotServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LotServiceApplication.class, args);
  }
}

@Component
class SampleData implements CommandLineRunner {

  private final LotRepository lotRepository;

  @Autowired
  SampleData(LotRepository lotRepository) {
    this.lotRepository = lotRepository;
  }

  @Override
  public void run(String... strings) throws Exception {
    Stream.of("Lot-A", "Lot-B", "Lot-C")
            .forEach(lot -> lotRepository.save(new Lot(lot)));
    lotRepository.findAll().forEach(System.out::println);
  }
}

@RestController @RefreshScope
class MessageRestController {
  private final String message;

  public MessageRestController(@Value("${message}") String message) {
    this.message = message;
  }

  @GetMapping("/message")
  String message() {
    return this.message;
  }
}

@RepositoryRestResource
interface LotRepository extends JpaRepository<Lot, Long> {
}

@Entity @Getter
class Lot {
  private Long id;

  private String lotName;

  Lot() {}

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
}
