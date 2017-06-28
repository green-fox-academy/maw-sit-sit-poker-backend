package com.greenfox.poker.model;


import java.util.List;

public class GamePlayerHand {

  private long gamePlayerHandId;
  private List<Card> gamePlayerOwnTwoCards;

  public GamePlayerHand() {
  }

  public long getGamePlayerHandId() {
    return gamePlayerHandId;
  }

  public void setGamePlayerHandId(long gamePlayerHandId) {
    this.gamePlayerHandId = gamePlayerHandId;
  }

  public List<Card> getGamePlayerOwnTwoCards() {
    return gamePlayerOwnTwoCards;
  }

  public void setGamePlayerOwnTwoCards(List<Card> gamePlayerOwnTwoCards) {
    this.gamePlayerOwnTwoCards = gamePlayerOwnTwoCards;
  }
}
