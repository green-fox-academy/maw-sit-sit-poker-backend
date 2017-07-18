package com.greenfox.poker.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GamePlayerDTO {

  @JsonProperty("user_id")
  private long userId;

  private List<Card> cards;

  public GamePlayerDTO() {
  }

  public GamePlayerDTO(long userId, List<Card> cards) {
    this.userId = userId;
    this.cards = cards;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }
}
