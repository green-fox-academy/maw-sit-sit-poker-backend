package com.greenfox.poker.gamestates;


import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

public class Betting {

  GameService gameService;

  @Autowired
  public Betting(GameService gameService) {
    this.gameService = gameService;
  }

  private void gameStateBettingInit(GameState gameState) {
    removeGamePlayersFromTableWithLessChipsThankBigBlind(gameState);
    checkIfThereAreAtLeastTwoPlayersToPlay(gameState);
    setAllPlayerAtTheTableToActive(gameState);
    assignDealer(gameState);
    autoBetSmallBlindBigBlind();
    drawPlayersCards();


  }

  private void removeGamePlayersFromTableWithLessChipsThankBigBlind(GameState gameState) {
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
////////////////////////////
    }
  }

  private void checkIfThereAreAtLeastTwoPlayersToPlay(GameState gameState) {
  }

  private void setAllPlayerAtTheTableToActive(GameState gameState) {
  }

  private void assignDealer(GameState gameState) {
  }

  private void autoBetSmallBlindBigBlind() {
  }

  private void drawPlayersCards() {
  }

}
