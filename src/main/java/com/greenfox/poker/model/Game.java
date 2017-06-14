package com.greenfox.poker.model;


import java.util.List;

public class Game {

  private long id;
  private String title;
  private int bigBlind;
  private int maxPlayersNum;
  private int currentPlayersNum;
  private long dealerId;
  private long actorId;
  private Round round;
  private List<GamePlayer> players;
  private Deck deck;
  private List<Card> cardsOnTable;
  private int pot;

  public Game() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getBigBlind() {
    return bigBlind;
  }

  public void setBigBlind(int bigBlind) {
    this.bigBlind = bigBlind;
  }

  public int getMaxPlayersNum() {
    return maxPlayersNum;
  }

  public void setMaxPlayersNum(int maxPlayersNum) {
    this.maxPlayersNum = maxPlayersNum;
  }

  public int getCurrentPlayersNum() {
    return currentPlayersNum;
  }

  public void setCurrentPlayersNum(int currentPlayersNum) {
    this.currentPlayersNum = currentPlayersNum;
  }

  public long getDealerId() {
    return dealerId;
  }

  public void setDealerId(long dealerId) {
    this.dealerId = dealerId;
  }

  public long getActorId() {
    return actorId;
  }

  public void setActorId(long actorId) {
    this.actorId = actorId;
  }

  public Round getRound() {
    return round;
  }

  public void setRound(Round round) {
    this.round = round;
  }

  public List<GamePlayer> getPlayers() {
    return players;
  }

  public void setPlayers(List<GamePlayer> players) {
    this.players = players;
  }

  public Deck getDeck() {
    return deck;
  }

  public void setDeck(Deck deck) {
    this.deck = deck;
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


