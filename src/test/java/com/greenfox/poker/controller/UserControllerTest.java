package com.greenfox.poker.controller;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.greenfox.poker.PokergameApplication;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.repository.PokerUserRepo;
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

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  private MockMvc mockMvc;

  @Autowired
  UserService userService;

  @Autowired
  PokerUserRepo pokerUserRepo;

  @Autowired
  private WebApplicationContext webApplicationContext;


  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  private long testUserId;
  private PokerUser testPokerUser;

  public void createTestPokerUser() {
    testPokerUser = new PokerUser();
    testPokerUser.setUsername("TestJeno");
    testPokerUser.setPassword("jenopass");
    testPokerUser.setEmail("jeno@kovacs.hu");
    pokerUserRepo.save(testPokerUser);
  }

  public void deleteTestPokerUser() {
    long testUserId = pokerUserRepo.findByUsername("TestJeno").get(0).getId();
    System.out.println(testUserId);
    pokerUserRepo.delete(testUserId);
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
  public void registerWithCorrectParameters() throws Exception {
    String register = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\", \"email\" :\"jeno@kovacs.hu\"}";
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
  public void registerWithMissingEmail() throws Exception {
    String register = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("Missing parameter(s): email!")));
  }

  @Test
  public void registerWithOccupiedEmail() throws Exception {
    createTestPokerUser();
    String register = "{\"username\" : \"TestJeno\", \"password\" : \"jenopass\", \"email\" : \"jeno@kovacs.hu\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.result", is("fail")))
        .andExpect(jsonPath("$.message", is("email address already exists")));
    deleteTestPokerUser();
  }
}
