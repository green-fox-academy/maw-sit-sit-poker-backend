package com.greenfox.poker.model;


import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

@Component
public class UserRegister {

  @NotBlank(message = "you cannot leave this field blank")
  private String username;

  @NotBlank(message = "you cannot leave this field blank")
  private String password;

  @NotBlank(message = "you cannot leave this field blank")
  private String email;

  private String avatar;

  public UserRegister() {
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

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
}
