package com.greenfox.poker.controller;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.greenfox.poker.PokergameApplication;
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
public class HomeControllerTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  private MockMvc mockMvc;

  @Autowired
  UserService userService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }


  @Test
  public void homeTest() throws Exception {

    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string("Hello"));
  }

  @Test
  public void loginWithCorrectDataTest() throws Exception{
    String login = "{\"username\" : \"Bond\", \"password\" : \"password123\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result",is("success")))
        .andExpect(jsonPath("$.token",is("ABC123")))
        .andExpect(jsonPath("$.id", is(4321)));
  }

  @Test
  public void loginWithMissingParameter() throws Exception{
    String login = "{\"username\" : \"Bond\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result",is("fail")))
        .andExpect(jsonPath("$.message",is("Missing parameter(s): [password]")));
  }

  @Test
  public void loginWithAnInvalidParameter() throws Exception{
    String login = "{\"username\" : \"Pisti\", \"password\" : \"password123\"}";
    this.mockMvc.perform(post("/login")
        .content(login)
        .contentType(contentType))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.result",is("fail")))
        .andExpect(jsonPath("$.message",is("invalid username or password")));
  }

  @Test
  public void registerWithCorrectParameters() throws Exception{
    String register = "{\"username\" : \"Pisti\", \"password\" : \"password123\", \"email\" :\"james@bond.com\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result",is("success")))
        .andExpect(jsonPath("$.token",is("ABC123")))
        .andExpect(jsonPath("$.id", is(4321)));
  }

  @Test
  public void registerWithMissingParameters() throws Exception{
    String register = "{\"username\" : \"Pisti\", \"password\" : \"password123\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result",is("fail")))
        .andExpect(jsonPath("$.message",is("Missing parameter(s): [email]")));
  }

  @Test
  public void registerWithOccupiedParameter() throws Exception{
    String register = "{\"username\" : \"Pisti\", \"password\" : \"password123\", \"email\" : \"occupied@email.com\"}";
    this.mockMvc.perform(post("/register")
        .content(register)
        .contentType(contentType))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.result",is("fail")))
        .andExpect(jsonPath("$.message",is("email address already exists")));
  }
}