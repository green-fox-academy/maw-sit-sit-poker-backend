package com.greenfox.poker.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PokerUserDTO {

  private long id;
  private String username;
  private String avatar;
  private long chips;

  public PokerUserDTO() {
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
