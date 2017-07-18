package com.greenfox.poker.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ShowDownResult {

  @JsonProperty("winner_user_id")
  private long winnerUserId;

  @JsonProperty("user_cards")
  private List<GamePlayer> userCards;

  public ShowDownResult() {
  }

  public ShowDownResult(long winnerUserId, List<GamePlayer> userCards) {
    this.winnerUserId = winnerUserId;
    this.userCards = userCards;
  }

  public long getWinnerUserId() {
    return winnerUserId;
  }

  public void setWinnerUserId(long winnerUserId) {
    this.winnerUserId = winnerUserId;
  }

  public List<GamePlayer> getUserCards() {
    return userCards;
  }

  public void setUserCards(List<GamePlayer> userCards) {
    this.userCards = userCards;
  }
}
