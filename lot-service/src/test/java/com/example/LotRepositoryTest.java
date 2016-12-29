package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 *
 * Created by zhwan on 16. 12. 28.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class LotRepositoryTest {
  @Autowired
  TestEntityManager entityManager;

  @Test
  public void test() {
    final Lot hello = entityManager.persistFlushFind(new Lot("hello"));
    assertThat(hello.getLotName()).isEqualTo("hello");
  }
}