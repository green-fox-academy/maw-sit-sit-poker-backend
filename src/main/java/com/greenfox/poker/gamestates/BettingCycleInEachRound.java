package com.greenfox.poker.gamestates;


import com.greenfox.poker.model.GameState;
import com.greenfox.poker.service.GameService;

public class BettingCycleInEachRound {

  GameState gameState;
  GameService gameService;

  public BettingCycleInEachRound(GameService gameService) {
    this.gameService = gameService;
  }

  public void bettingInBettingState(long gameStateId) {
    gameState = gameService.getGameStateMap().get(gameStateId);



  }

  public void bettingInFlopTurnRiverShowdownState(long gameStateId) {
  }
}
