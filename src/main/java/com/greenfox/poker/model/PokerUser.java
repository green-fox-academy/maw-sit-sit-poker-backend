package com.greenfox.poker.model;


import com.sun.istack.internal.Nullable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

@Component
public class PokerUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  private String username;

  @NotNull
  private String password;

  @NotNull
  @Email
  private String email;

  @URL
  private String avatar;
  private long cash;

  public PokerUser() {
    this.cash = 10000;
  }

  public PokerUser(String username, String password, String email, String avatar) {
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
