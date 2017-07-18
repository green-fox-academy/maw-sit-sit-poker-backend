package com.greenfox.poker.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ShowDownResult {

  @JsonProperty("winner_user_id")
  private long winnerUserId;

  @JsonProperty("user_cards")
  private List<GamePlayerDTO> userCards;

  public ShowDownResult() {
  }

  public ShowDownResult(long winnerUserId, List<GamePlayerDTO> userCards) {
    this.winnerUserId = winnerUserId;
    this.userCards = userCards;
  }

  public long getWinnerUserId() {
    return winnerUserId;
  }

  public void setWinnerUserId(long winnerUserId) {
    this.winnerUserId = winnerUserId;
  }

  public List<GamePlayerDTO> getUserCards() {
    return userCards;
  }

  public void setUserCards(List<GamePlayerDTO> userCards) {
    this.userCards = userCards;
  }
}
