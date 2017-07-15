package com.greenfox.poker.model;


import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GamePlayerHand {

  private long gameStateId;
  private List<Card> playerCards;

  public GamePlayerHand() {
  }

  public long getGameStateId() {
    return gameStateId;
  }

  public void setGameStateId(long gameStateId) {
    this.gameStateId = gameStateId;
  }

  public List<Card> getPlayerCards() {
    return playerCards;
  }

  public void setPlayerCards(List<Card> playerCards) {
    this.playerCards = playerCards;
  }

  public void addCardToGamePlayerHand(Card cardToAdd){
    playerCards.add(cardToAdd);
  }
}
