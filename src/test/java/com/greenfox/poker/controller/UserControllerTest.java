package com.greenfox.poker.controller;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.greenfox.poker.PokergameApplication;
import com.greenfox.poker.mockbuilder.MockPokerUserBuilder;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.repository.PokerUserRepo;
import com.greenfox.poker.service.TokenService;
import com.greenfox.poker.service.UserService;
import java.nio.charset.Charset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@WebAppConfiguration
@EnableWebMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  MockPokerUserBuilder mockPokerUserBuilder;

  @Autowired
  UserService userService;

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
    Mockito.when(pokerUserRepo.findByUsername("TestJeno")).thenReturn(mockUser);
    Mockito.when(pokerUserRepo.existsByPassword("jenopass")).thenReturn(true);
    Mockito.when(pokerUserRepo.existsByUsername("TestJeno")).thenReturn(true);
    Mockito.when(pokerUserRepo.findOne(0L)).thenReturn(mockUser);
    String login = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\"}";
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
    String login = "{\"username\" : \"TestJeno\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): password!")));
  }

  @Test
  public void testPokerUserLoginWithInvalidPassword() throws Exception {
    String login = "{\"username\" : \"TestJeno\", \"password\" : \"invalidpassword\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("invalid username or password")));
  }

  @Test
  public void testPokerUserLoginWithMissingUsername() throws Exception {
    String login = "{\"password\" : \"jenopass\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): username!")));
  }

  @Test
  public void testPokerUserLoginWithInvalidUsername() throws Exception {
    String login = "{\"username\" : \"InvalidTestJeno\", \"password\" : \"jenopass\"}";
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
    Mockito.when(pokerUserRepo.findOne(0L)).thenReturn(mockUser);
    String register = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\", \"email\" : \"jeno@kovacs.hu\"}";
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
    Mockito.when(pokerUserRepo.existsByUsername("TestJeno")).thenReturn(true);
    String register = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\", \"email\" : \"jenoTwin@kovacs.hu\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("username already exists")));
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

  @Test
  public void testRegisterWithOccupiedEmail() throws Exception {
    Mockito.when(pokerUserRepo.existsByEmail("jeno@kovacs.hu")).thenReturn(true);
    String register = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\", \"email\" : \"jeno@kovacs.hu\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("email address already exists")));
  }
}
