package com.greenfox.poker.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.stereotype.Component;

@Component
public class LoginRequest {

  @NotNull
  String username;
  @NotNull
  String password;

  public LoginRequest() {
  }

  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

