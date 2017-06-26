package com.greenfox.poker.controller;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.greenfox.poker.PokergameApplication;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.repository.GameRepo;
import com.greenfox.poker.repository.PokerUserRepo;
import com.greenfox.poker.service.TokenService;
import com.greenfox.poker.service.UserService;
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
public class UserControllerTest {

  private MockMvc mockMvc;
  private PokerUser testPokerUser;
  private long testGameId;
  private String token = createValidTokenForTesting();
  private Game testGame;

  @Autowired
  UserService userService;

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
    testPokerUser.setUsername("TestJeno");
    testPokerUser.setPassword("jenopass");
    testPokerUser.setEmail("jeno@kovacs.hu");
    pokerUserRepo.save(testPokerUser);
  }

  private void deleteTestPokerUser() {
    long testUserId = pokerUserRepo.findByUsername("TestJeno").getId();
    System.out.println(testUserId);
    pokerUserRepo.delete(testUserId);
  }

  private String createValidTokenForTesting(){
    return tokenService.generateToken(testPokerUser);

  }

  private void createTestGame(){
    testGame = new Game("test", 20, 3);
    gameRepo.save(testGame);
    testGameId = gameRepo.findOneByName("test").getId();
  }

  private void deleteTestGame(){
    gameRepo.delete(testGameId);
  }

  @Test
  public void testPokerUserLoginWithValidData() throws Exception {
    createTestPokerUser();
    String login = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.id").exists());
    deleteTestPokerUser();

  }

  @Test
  public void testPokerUserLoginWithMissingPassword() throws Exception {
    createTestPokerUser();
    String login = "{\"username\" : \"TestJeno\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): password!")));
    deleteTestPokerUser();
  }

  @Test
  public void testPokerUserLoginWithInvalidPassword() throws Exception {
    createTestPokerUser();
    String login = "{\"username\" : \"TestJeno\", \"password\" : \"invalidpassword\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("invalid username or password")));
    deleteTestPokerUser();
  }

  @Test
  public void testPokerUserLoginWithMissingUsername() throws Exception {
    createTestPokerUser();
    String login = "{\"password\" : \"jenopass\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): username!")));
    deleteTestPokerUser();
  }

  @Test
  public void testPokerUserLoginWithInvalidUsername() throws Exception {
    createTestPokerUser();
    String login = "{\"username\" : \"InvalidTestJeno\", \"password\" : \"jenopass\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("invalid username or password")));
    deleteTestPokerUser();
  }

  @Test
  public void testRegisterWithValidParameters() throws Exception {
    String register = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\", \"email\" : \"jeno@kovacs.hu\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.id").exists());
    deleteTestPokerUser();
  }

  @Test
  public void testRegisterWithMissingUsername() throws Exception {
    String register = "{\"password\" : \"jenopass\", \"email\" :\"jeno@kovacs.hu\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): username!")));
  }

  @Test
  public void testRegisterWithOccupiedUsername() throws Exception {
    createTestPokerUser();
    String register = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\", \"email\" : \"jenoTwin@kovacs.hu\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("username already exists")));
    deleteTestPokerUser();
  }

  @Test
  public void testRegisterWithMissingPassword() throws Exception {
    String register = "{\"username\" : \"TestJeno\", \"email\" : \"jeno@kovacs.hu\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): password!")));
  }

  @Test
  public void testRegisterWithMissingEmail() throws Exception {
    String register = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): email!")));
  }

//  @Test
//  public void testRegisterWithOccupiedEmail() throws Exception {
//    createTestPokerUser();
//    String register = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\", \"email\" : \"jeno@kovacs.hu\"}";
//    this.mockMvc.perform(post("/register")
//        .content(register)
//        .contentType(contentType))
//        .andExpect(status().isConflict())
//        .andExpect(jsonPath("$.result", is("fail")))
//        .andExpect(jsonPath("$.message", is("email address already exists")));
//    deleteTestPokerUser();
//  }

  public void testJoinWithExistingTable() throws Exception{
    createTestGame();
    String join = "{\"chips\" : \"2000\"}";
    this.mockMvc.perform(post("/game/1/join")
        .content(join)
        .header("X-poker-token", token)
        .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.message", is(testPokerUser.getUsername() + " joined game: " + testGame.getName())));
    deleteTestPokerUser();

  }
}
