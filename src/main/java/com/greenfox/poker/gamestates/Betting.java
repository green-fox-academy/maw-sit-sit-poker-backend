package com.greenfox.poker.gamestates;


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


    assignDealer(gameState);

  }

  private void assignDealer(GameState gameState) {
  }

}
