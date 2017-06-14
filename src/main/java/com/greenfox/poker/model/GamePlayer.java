package com.greenfox.poker.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.stereotype.Component;

@Entity
@Component
public class GamePlayer {

  @Id
  private long id;
  private String username;
  private String email;
  private String avatar;
  private int chips;

  public GamePlayer() {
  }

  public GamePlayer(long id, String username, String email, String avatar, int chips) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.avatar = avatar;
    this.chips = chips;
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

  public int getChips() {
    return chips;
  }

  public void setChips(int chips) {
    this.chips = chips;
  }
}
