package com.greenfox.poker.controller;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.greenfox.poker.PokergameApplication;
import com.greenfox.poker.mockbuilder.MockPokerUserBuilder;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.repository.PokerUserRepo;
import com.greenfox.poker.service.DtoService;
import com.greenfox.poker.service.TokenService;
import com.greenfox.poker.service.UserService;
import java.nio.charset.Charset;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.mockito.Mockito;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PokergameApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableWebMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  MockPokerUserBuilder mockPokerUserBuilder;
  @Autowired
  UserService userService;
  @Autowired
  DtoService dtoService;
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
  public void testPokerUserLoginWithValidData() throws Exception {
    PokerUser mockUser = mockPokerUserBuilder.build();
    Mockito.when(pokerUserRepo.findByUsername("Pisti")).thenReturn(mockUser);
    Mockito.when(pokerUserRepo.existsByPassword("password123")).thenReturn(true);
    Mockito.when(pokerUserRepo.existsByUsername("Pisti")).thenReturn(true);
    Mockito.when(pokerUserRepo.findOne(1l)).thenReturn(mockUser);
    String login = "{\"username\" : \"Pisti\", \"password\" : \"password123\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void testPokerUserLoginWithMissingPassword() throws Exception {
    String login = "{\"username\" : \"Pisti\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): password!")));
  }

  @Test
  public void testPokerUserLoginWithInvalidPassword() throws Exception {
    String login = "{\"username\" : \"Pisti\", \"password\" : \"invalidpassword\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("invalid username or password")));
  }

  @Test
  public void testPokerUserLoginWithMissingUsername() throws Exception {
    String login = "{\"password\" : \"password123\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): username!")));
  }

  @Test
  public void testPokerUserLoginWithInvalidUsername() throws Exception {
    String login = "{\"username\" : \"InvalidPisti\", \"password\" : \"password123\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("invalid username or password")));
  }

  @Test
  public void testRegisterWithValidParameters() throws Exception {
    PokerUser mockUser = mockPokerUserBuilder.build();
    Mockito.when(pokerUserRepo.findOne(1l)).thenReturn(mockUser);
    Mockito.when(pokerUserRepo.existsByEmail("pisti@pisti.com")).thenReturn(false);
    Mockito.when(pokerUserRepo.existsByUsername("Pisti")).thenReturn(false);
    Mockito.when(pokerUserRepo.findByUsername("Pisti")).thenReturn(mockUser);
    String token = tokenService.generateToken(mockUser);
    mockUser.setToken(token);
    pokerUserRepo.save(mockUser);
    dtoService.makePokerUserDTO(mockUser);
    String register = "{\"username\" : \"Pisti\", \"password\" : \"password123\", \"email\" : \"pisti@pisti.com\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void testRegisterWithMissingUsername() throws Exception {
    String register = "{\"password\" : \"password123\", \"email\" :\"pisti@pisti.com\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): username!")));
  }

  @Test
  public void testRegisterWithOccupiedUsername() throws Exception {
    Mockito.when(pokerUserRepo.existsByUsername("Pisti")).thenReturn(true);
    String register = "{\"username\" : \"Pisti\", \"password\" : \"password123\", \"email\" : \"pisti@pisti.com\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("username already exists")));
  }

  @Test
  public void testRegisterWithMissingPassword() throws Exception {
    String register = "{\"username\" : \"Pisti\", \"email\" : \"pisti@pisti.com\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): password!")));
  }

  @Test
  public void testRegisterWithMissingEmail() throws Exception {
    String register = "{\"username\" : \"Pisti\", \"password\" : \"password123\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): email!")));
  }

  @Test
  public void testRegisterWithOccupiedEmail() throws Exception {
    Mockito.when(pokerUserRepo.existsByEmail("pisti@pisti.com")).thenReturn(true);
    String register = "{\"username\" : \"Pisti\", \"password\" : \"password123\", \"email\" : \"pisti@pisti.com\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("email address already exists")));
  }

  @Test
  public void testGetUserInfoWithValidTokenAndId() throws Exception {
    PokerUser mockUser = mockPokerUserBuilder.build();
    String token = tokenService.generateToken(mockUser);
    Mockito.when(pokerUserRepo.exists(1l)).thenReturn(true);
    Mockito.when(pokerUserRepo.findOne(1l)).thenReturn(mockUser);
    Mockito.when(pokerUserRepo.existsByToken(token)).thenReturn(true);
    this.mockMvc.perform(get("/user/1")
        .contentType(contentType)
        .header("X-poker-token", token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.username", is("Pisti")));
  }

  @Test
  public void testGetUserInfoWithValidTokenWrongId() throws Exception {
    PokerUser mockUser = mockPokerUserBuilder.build();
    String token = tokenService.generateToken(mockUser);
    Mockito.when(pokerUserRepo.existsByToken(token)).thenReturn(true);
    this.mockMvc.perform(get("/user/0")
        .contentType(contentType)
        .header("X-poker-token", token))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("user doesn't exist")));
  }

  @Test
  public void testGetLeaderBoard() throws Exception {
    PokerUser mockUser = mockPokerUserBuilder.build();
    List<PokerUser> listOfMockUser = mockPokerUserBuilder.createListOfMockPokerUser();
    String token = tokenService.generateToken(mockUser);
    Mockito.when(pokerUserRepo.existsByToken(token)).thenReturn(true);
    Mockito.when(pokerUserRepo.findOne(1l)).thenReturn(mockUser);
    Mockito.when(pokerUserRepo.findTop10ByOrderByChipsDesc()).thenReturn(listOfMockUser);
    this.mockMvc.perform(get("/leaderboard")
        .contentType(contentType)
        .header("X-poker-token", token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(10)))
        .andExpect(jsonPath("$.[1].id", is(1)))
        .andExpect(jsonPath("$.[1].username", is("Pisti")));
  }
}
