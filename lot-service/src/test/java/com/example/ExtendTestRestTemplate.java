package com.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by zhwan on 16. 12. 28.
 */
public final class ExtendTestRestTemplate {
  private ExtendTestRestTemplate() {
  }

  public static final TestRestTemplate restTemplate() {
    return new TestRestTemplate(customRestTemplate());
  }

  private static RestTemplate customRestTemplate() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.registerModule(new Jackson2HalModule());

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));
//    converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
    converter.setObjectMapper(mapper);

    return new RestTemplate(Arrays.asList(converter));
  }
}
