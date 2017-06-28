package com.greenfox.poker.controller;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import com.greenfox.poker.PokergameApplication;
import com.greenfox.poker.mockbuilder.MockGameBuilder;
import com.greenfox.poker.mockbuilder.MockPokerUserBuilder;
import com.greenfox.poker.repository.GameRepo;
import com.greenfox.poker.repository.PokerUserRepo;
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
public class GameControllerTest {

  @Autowired
  GameService gameService;
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
  public void testJoinPlayerToGame() throws Exception{

    Mockito.when(pokerUserRepo.findByUsername("Pisti")).thenReturn(mockPokerUserBuilder.build());
    Mockito.when(pokerUserRepo.findOne(1l)).thenReturn(mockPokerUserBuilder.build());
    Mockito.when(pokerUserRepo.existsByUsername("Pisti")).thenReturn(true);
    Mockito.when(pokerUserRepo.exists(1l)).thenReturn(true);
    Mockito.when(gameRepo.exists(1l)).thenReturn(true);
    Mockito.when(gameRepo.findOne(1l)).thenReturn(mockGameBuilder.build());
    Mockito.when(gameRepo.findOneByName("Table")).thenReturn(mockGameBuilder.build());

    String join = "{\"chips\" : \"2000\"}";
    final String token = tokenService.generateToken(mockPokerUserBuilder.build());
    this.mockMvc.perform(post("/game/1/join")
        .content(join)
        .header("X-poker-token", token)
        .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.message", is(mockPokerUserBuilder.build().getUsername() + " joined game: " + mockGameBuilder.build().getName())));
  }
}


