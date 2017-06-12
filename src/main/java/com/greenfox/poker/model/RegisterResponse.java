package com.greenfox.poker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.stereotype.Component;

@Component
@JsonInclude(Include.NON_NULL)
public class RegisterResponse {

  private long id;
  private String username;
  private String password;
  private String email;
  private long cash;
  private String avatar; JsonInclude ALWAYS;
  private String status;
  private String message;

  public RegisterResponse() {
  }

  public RegisterResponse(long id, String username, String email, String avatar) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.cash = 10000;
    this.avatar = avatar;
  }

  public RegisterResponse(String message) {
    this.message = message;
    this.status = "error";
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public long getCash() {
    return cash;
  }

  public void setCash(long cash) {
    this.cash = cash;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
