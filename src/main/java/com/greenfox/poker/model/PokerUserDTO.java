package com.greenfox.poker.model;

import org.springframework.stereotype.Component;

@Component
public class PokerUserDTO implements ResponseType {

  private Long id;
  private String username;
  private String avatar;
  private long chips;

  public PokerUserDTO() {
  }

  public PokerUserDTO(long chips){
    this.chips = chips;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
