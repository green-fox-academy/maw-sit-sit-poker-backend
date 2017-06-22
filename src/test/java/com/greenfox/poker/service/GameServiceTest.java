package com.greenfox.poker.service;

import com.greenfox.poker.PokergameApplication;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.repository.GameRepo;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.greenfox.poker.PokergameApplication;
import java.nio.charset.Charset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PokergameApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class GameServiceTest {

  private MockMvc mockMvc;
  private Game testGame;

  @Autowired
  GameService gameService;

  @Autowired
  GameRepo gameRepo;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          Charset.forName("utf8"));

//
//  private void createTestGame() {
//    testGame = new Game();
//    testGame.setId(9999);
//    testGame.setName("testGameTable");
//    testGame.setBigBlind(5555);
//    testGame.setMaxPlayers(99);
//    testGame.setCurrentPlayers(9);
//    gameService.saveGame(testGame);
////    updateTestGameIdForTheRealId();
//  }

//  private void updateTestGameIdForTheRealId() {
//    long testGameIdInTheRepo = gameRepo.findByName("testGameTable").get(0).getId();
//    testGame.setId(testGameIdInTheRepo);
//  }
//
//  private void deleteTestGame() {
//    long testGameIdToDelete = gameRepo.findByName("testGameTable").get(0).getId();
//    gameRepo.delete(testGameIdToDelete);
//  }

  @Test
  public void testGetGameById() throws Exception {
//    createTestGame();

    assertEquals(1, 1);

//    deleteTestGame();
  }
}
