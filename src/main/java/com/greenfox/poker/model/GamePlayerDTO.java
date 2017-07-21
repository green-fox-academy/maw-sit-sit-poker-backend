package com.greenfox.poker.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonPropertyOrder({"userId", "cards"})
public class GamePlayerDTO {

  @JsonProperty("user_id")
  private long userId;

  private List<String> cards;

  public GamePlayerDTO() {
  }

  public GamePlayerDTO(long userId, List<String> cards) {
    this.userId = userId;
    this.cards = cards;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public List<String> getCards() {
    return cards;
  }

  public void setCards(List<String> cards) {
    this.cards = cards;
  }
}
