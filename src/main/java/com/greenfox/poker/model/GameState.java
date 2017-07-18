package com.greenfox.poker.model;



import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GameState {
  private long id;
  private List<GamePlayer> players;
  private Long actorPlayerId;
  private Long dealerPlayerId;
  private Round round;
  private List<Card> cardsOnTable;
  private int pot;

  @JsonIgnore
  private Long smallBlindPlayerId;

  @JsonIgnore
  private Long bigBlindPlayerId;

  @JsonIgnore
  private Deck deckInGameState;

  public GameState() {
    this.players = new ArrayList<>();
  }

  public GameState(long id) {
    this.id = id;
    this.players = new ArrayList<>();
  }

  public Deck getDeckInGameState() {
    return deckInGameState;
  }

  public void setDeckInGameState(Deck deckInGameState) {
    this.deckInGameState = deckInGameState;
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

  public Long getSmallBlindPlayerId() {
    return smallBlindPlayerId;
  }

  public void setSmallBlindPlayerId(Long smallBlindPlayerId) {
    this.smallBlindPlayerId = smallBlindPlayerId;
  }

  public Long getBigBlindPlayerId() {
    return bigBlindPlayerId;
  }

  public void setBigBlindPlayerId(Long bigBlindPlayerId) {
    this.bigBlindPlayerId = bigBlindPlayerId;
  }

  @Override
  public String toString() {
    return "GameState{" +
            "id=" + id +
            ", players=" + players +
            ", actorPlayerId=" + actorPlayerId +
            ", dealerPlayerId=" + dealerPlayerId +
            ", round=" + round +
            ", cardsOnTable=" + cardsOnTable +
            ", pot=" + pot +
            ", smallBlindPlayerId=" + smallBlindPlayerId +
            ", bigBlindPlayerId=" + bigBlindPlayerId +
            ", deckInGameState=" + deckInGameState +
            '}';
  }
}
