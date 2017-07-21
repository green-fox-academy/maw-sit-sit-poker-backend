package com.greenfox.poker.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ShowDownResult {

  @JsonProperty("winner_user_ids")
  private List<Long> winnerUserIds;

  @JsonProperty("user_cards")
  private List<GamePlayerDTO> userCards;

  public ShowDownResult() {
  }

  public ShowDownResult(List<Long> winnerUserIds, List<GamePlayerDTO> userCards) {
    this.winnerUserIds = winnerUserIds;
    this.userCards = userCards;
  }

  public List<Long> getWinnerUserIds() {
    return winnerUserIds;
  }

  public void setWinnerUserIds(List<Long> winnerUserIds) {
    this.winnerUserIds = winnerUserIds;
  }

  public List<GamePlayerDTO> getUserCards() {
    return userCards;
  }

  public void setUserCards(List<GamePlayerDTO> userCards) {
    this.userCards = userCards;
  }
}
