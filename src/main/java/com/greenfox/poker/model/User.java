package com.greenfox.poker.model;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

@Component
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotBlank(message = "you cannot leave this field blank")
  private String username;

  @NotBlank(message = "you cannot leave this field blank")
  private String password;

  @NotBlank(message = "you cannot leave this field blank")
  private String email;

  private String avatar;
  private long cash;

  public User() {
    this.cash = 10000;
  }

  public User(String username, String password, String email, String avatar) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.avatar = avatar;
    this.cash = 10000;
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

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public long getCash() {
    return cash;
  }

  public void setCash(long cash) {
    this.cash = cash;
  }
}
