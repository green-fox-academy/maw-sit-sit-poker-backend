package com.greenfox.poker;

import com.greenfox.poker.service.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PokergameApplicationTests implements CommandLineRunner {

  @Autowired
  GameService gameService;

  @Test
  public void contextLoads() {
  }

  @Override
  public void run(String... args) throws Exception {
    gameService.initDefaultGames();
  }
}
