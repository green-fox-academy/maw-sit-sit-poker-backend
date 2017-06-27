package com.greenfox.poker.model;


import java.util.List;

public class GameState {
  private long id;
  private List<GamePlayer> players;
  private Long actorPlayerId;
  private Long dealerPlayerId;
  private Round round;
  private List<Card> cardsOnTable;
  private int pot;

  public GameState() {
  }

  public GameState(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<GamePlayer> getPlayers() {
    return players;
  }

  public void setPlayers(List<GamePlayer> players) {
    this.players = players;
  }

  public Round getRound() {
    return round;
  }

  public void setRound(Round round) {
    this.round = round;
  }

  public Long getActorPlayerId() {
    return actorPlayerId;
  }

  public void setActorPlayerId(Long actorPlayerId) {
    this.actorPlayerId = actorPlayerId;
  }

  public Long getDealerPlayerId() {
    return dealerPlayerId;
  }

  public void setDealerPlayerId(Long dealerPlayerId) {
    this.dealerPlayerId = dealerPlayerId;
  }

  public List<Card> getCardsOnTable() {
    return cardsOnTable;
  }

  public void setCardsOnTable(List<Card> cardsOnTable) {
    this.cardsOnTable = cardsOnTable;
  }

  public int getPot() {
    return pot;
  }

  public void setPot(int pot) {
    this.pot = pot;
  }
}
