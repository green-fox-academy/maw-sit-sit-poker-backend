package com.greenfox.poker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.stereotype.Component;

@Entity
@Component
public class PokerUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  private String username;

  @NotNull
  private String password;

  @NotNull
  @Email
  private String email;

  private String avatar;
  private long chips;

  public PokerUser() {
    this.chips = 10000;
  }

  public PokerUser(String username, String password, String email, String avatar) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.avatar = avatar;
    this.chips = 10000;
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

  public long getChips() {
    return chips;
  }

  public void setChips(long chips) {
    this.chips = chips;
  }
}
