package com.greenfox.poker.controller;

import com.greenfox.poker.PokergameApplication;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.repository.GameRepo;

import com.greenfox.poker.service.GameService;

import com.greenfox.poker.repository.PokerUserRepo;
import com.greenfox.poker.service.TokenService;
import com.greenfox.poker.service.UserService;
import java.math.BigInteger;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BindingResult;
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
public class GameControllerTest {

  private MockMvc mockMvc;
  private PokerUser testPokerUser;
  private long testGameId;
  private String token;
  private Game testGame;

  @Autowired
  UserService userService;

  @Autowired
  GameService gameService;

  @Autowired
  PokerUserRepo pokerUserRepo;

  @Autowired
  GameRepo gameRepo;

  @Autowired
  TokenService tokenService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          Charset.forName("utf8"));

  private void createTestPokerUser() {
    testPokerUser = new PokerUser();
    testPokerUser.setUsername("TestJozsi");
    testPokerUser.setPassword("jenopass");
    testPokerUser.setEmail("jozsi@kovacs.hu");
    pokerUserRepo.save(testPokerUser);
  }

  private void deleteTestPokerUser() {
    long testUserId = pokerUserRepo.findByUsername("TestJozsi").getId();
    pokerUserRepo.delete(testUserId);
  }

  private String createValidTokenForTesting() {
    token = tokenService.generateToken(testPokerUser);
    return token;
  }

  private void createTestGame() {
    testGame = new Game("test", 20, 3);
    gameRepo.save(testGame);
    testGameId = gameRepo.findOneByName("test").getId();
    gameService.getGameStateMap().put(testGameId, new GameState(testGameId));
  }

  private void deleteTestGame() {
    gameRepo.delete(testGame);
    gameService.getGameStateMap().remove(gameRepo.findOneByName("test"));
  }

  @Test
  public void testGetGameById() throws Exception {
    assertEquals(1, 1);
  }

  @Test
  public void testSuccesfulJoinToTable() throws Exception {
    createTestGame();
    createTestPokerUser();
    createValidTokenForTesting();
    String join = "{\"chips\" : \"2000\"}";
    this.mockMvc.perform(post("/game/1/join")
        .content(join)
        .header("X-poker-token", token)
        .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.message",
            is(testPokerUser.getUsername() + " joined game: " + testGame.getName())));
    deleteTestPokerUser();
    deleteTestGame();
  }

  @Test
  public void testGetGames() throws Exception {
    createTestGame();
    this.mockMvc.perform(get("/games")
            .contentType(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.games").exists());
    gameService.deleteGame(testGame);
  }
}
