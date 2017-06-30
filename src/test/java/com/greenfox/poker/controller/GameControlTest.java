package com.greenfox.poker.controller;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import com.greenfox.poker.PokergameApplication;
import com.greenfox.poker.mockbuilder.MockGameBuilder;
import com.greenfox.poker.mockbuilder.MockPokerUserBuilder;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.repository.GameRepo;
import com.greenfox.poker.repository.PokerUserRepo;
import com.greenfox.poker.service.DtoService;
import com.greenfox.poker.service.GameService;
import com.greenfox.poker.service.TokenService;
import java.nio.charset.Charset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PokergameApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableWebMvc
public class GameControlTest {

  @Autowired
  GameService gameService;
  @Autowired
  DtoService dtoService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  MockPokerUserBuilder mockPokerUserBuilder;
  @Autowired
  MockGameBuilder mockGameBuilder;
  @MockBean
  GameRepo gameRepo;
  @MockBean
  PokerUserRepo pokerUserRepo;
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

  @Test
  public void testSuccessfulJoinToGame() throws Exception {
    PokerUser player = mockPokerUserBuilder.build();
    Game game = mockGameBuilder.build();
    String token = tokenService.generateToken(player);
    String join = "{\"chips\" : \"2000\"}";
    Mockito.when(pokerUserRepo.findByUsername("Pisti")).thenReturn(player);
    Mockito.when(pokerUserRepo.findOne(1l)).thenReturn(player);
    Mockito.when(pokerUserRepo.existsByUsername("Pisti")).thenReturn(true);
    Mockito.when(pokerUserRepo.exists(1l)).thenReturn(true);
    Mockito.when(pokerUserRepo.existsByToken(token)).thenReturn(true);
    Mockito.when(gameRepo.exists(1l)).thenReturn(true);
    Mockito.when(gameRepo.exists(2l)).thenReturn(false);
    Mockito.when(gameRepo.findOne(1l)).thenReturn(game);
    Mockito.when(gameRepo.findOneByName("Table")).thenReturn(game);
    dtoService.makePokerUserDTO(player);
    gameService.createNewGame(game);

    mockMvc.perform(post("/game/1/join")
        .content(join)
        .header("X-poker-token", token)
        .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.message", is(player.getUsername() + " joined game: " + game.getName())));
  }

  @Test
  public void testJoinWithNonExistingGameId() throws Exception{
    PokerUser player = mockPokerUserBuilder.build();
    Game game = mockGameBuilder.build();
    String token = tokenService.generateToken(player);
    String join = "{\"chips\" : \"2000\"}";
    Mockito.when(pokerUserRepo.findByUsername("Pisti")).thenReturn(player);
    Mockito.when(pokerUserRepo.findOne(1l)).thenReturn(player);
    Mockito.when(pokerUserRepo.existsByUsername("Pisti")).thenReturn(true);
    Mockito.when(pokerUserRepo.exists(1l)).thenReturn(true);
    Mockito.when(pokerUserRepo.existsByToken(token)).thenReturn(true);
    Mockito.when(gameRepo.exists(1l)).thenReturn(true);
    Mockito.when(gameRepo.exists(2l)).thenReturn(false);
    Mockito.when(gameRepo.findOne(1l)).thenReturn(game);
    Mockito.when(gameRepo.findOneByName("Table")).thenReturn(game);
    dtoService.makePokerUserDTO(player);
    gameService.createNewGame(game);

    mockMvc.perform(post("/game/2/join")
        .content(join)
        .header("X-poker-token", token)
        .contentType(contentType))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("game id doesn't exist")));
  }

  @Test
  public void testJoinWithNotEnoughChips() throws Exception {
    PokerUser player = mockPokerUserBuilder.build();
    Game game = mockGameBuilder.build();
    String token = tokenService.generateToken(mockPokerUserBuilder.build());
    String join = "{\"chips\" : \"12000\"}";
    Mockito.when(pokerUserRepo.findByUsername("Pisti")).thenReturn(player);
    Mockito.when(pokerUserRepo.findOne(1l)).thenReturn(player);
    Mockito.when(pokerUserRepo.existsByUsername("Pisti")).thenReturn(true);
    Mockito.when(pokerUserRepo.exists(1l)).thenReturn(true);
    Mockito.when(pokerUserRepo.existsByToken(token)).thenReturn(true);
    Mockito.when(gameRepo.exists(1l)).thenReturn(true);
    Mockito.when(gameRepo.findOne(1l)).thenReturn(game);
    Mockito.when(gameRepo.findOneByName("Table")).thenReturn(game);
    dtoService.makePokerUserDTO(player);
    gameService.createNewGame(game);

    mockMvc.perform(post("/game/1/join")
        .content(join)
        .header("X-poker-token", token)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("you dont have enough chips to play with")));
  }
  //
  @Test
  public void testJoinWithPlayerAlreadySittigAtTable() throws Exception {
    PokerUser player = mockPokerUserBuilder.build();
    Game game = mockGameBuilder.build();
    String token = tokenService.generateToken(player);
    String join = "{\"chips\" : \"2000\"}";
    Mockito.when(pokerUserRepo.findByUsername("Pisti")).thenReturn(player);
    Mockito.when(pokerUserRepo.findOne(1l)).thenReturn(player);
    Mockito.when(pokerUserRepo.existsByUsername("Pisti")).thenReturn(true);
    Mockito.when(pokerUserRepo.exists(1l)).thenReturn(true);
    Mockito.when(pokerUserRepo.existsByToken(token)).thenReturn(true);
    Mockito.when(gameRepo.exists(1l)).thenReturn(true);
    Mockito.when(gameRepo.exists(2l)).thenReturn(false);
    Mockito.when(gameRepo.findOne(1l)).thenReturn(game);
    Mockito.when(gameRepo.findOneByName("Table")).thenReturn(game);
    dtoService.makePokerUserDTO(player);
    gameService.createNewGame(game);
    gameService.createNewPlayerAndAddToGame(player.getId(), game.getId(), 5000);

    mockMvc.perform(post("/game/{id}/join", 1l)
        .content(join)
        .header("X-poker-token", token)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is(player.getUsername() + " already joined game: " + game.getName())));
  }

  @Test
  public void testGetGameEndpointWithCorrectId() throws Exception {
    Game game = mockGameBuilder.build();
    Mockito.when(gameRepo.exists(1l)).thenReturn(true);
    Mockito.when(gameRepo.findOne(1l)).thenReturn(game);
    Mockito.when(gameRepo.findOneByName("Table")).thenReturn(game);
    gameService.createNewGame(game);

    this.mockMvc.perform(get("/game/{id}", 1l)
        .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("Table")))
        .andExpect(jsonPath("$.big_blind", is(20)))
        .andExpect(jsonPath("$.max_players", is(3)));
  }
}
