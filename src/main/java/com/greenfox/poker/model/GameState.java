package com.greenfox.poker.model;


import java.util.List;

public class GameState {

  private Game game;
  private List<GamePlayer> players;
  private long actorPlayerId;
  private long dealerPlayerId;
  private Round round;
  private List<Card> cardsOnTable;
  private int pot;

  public GameState() {
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
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

  public long getActorPlayerId() {
    return actorPlayerId;
  }

  public void setActorPlayerId(long actorPlayerId) {
    this.actorPlayerId = actorPlayerId;
  }

  public long getDealerPlayerId() {
    return dealerPlayerId;
  }

  public void setDealerPlayerId(long dealerPlayerId) {
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
