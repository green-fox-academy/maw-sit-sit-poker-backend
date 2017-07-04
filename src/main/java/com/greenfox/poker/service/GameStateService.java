package com.greenfox.poker.service;


import com.greenfox.poker.gamestates.Betting;
import com.greenfox.poker.gamestates.Flop;
import com.greenfox.poker.gamestates.RiverAndTurn;
import com.greenfox.poker.gamestates.ShowDown;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GameState;
import org.springframework.beans.factory.annotation.Autowired;

public class GameStateService {

  GameService gameService;

//  BettingCycleInEachRound bettingCycleInEachRound = new BettingCycleInEachRound(gameService);

  @Autowired
  public GameStateService(GameService gameService) {
    this.gameService = gameService;
  }


  private void bettingRound(GameState gameState){
    Betting betting = new Betting();
    Game game = gameService.getGameById(gameState.getId());
    betting.initForBettingGameState(gameState, game);
  }

  private void flopRound(GameState gameState){
    Flop flop = new Flop();
    flop.initForBettingGameState(gameState);
  }

  private void turnRound(GameState gameState) {
    RiverAndTurn turn  = new RiverAndTurn();
    turn.initForRiverAndTurnGameState(gameState);
  }

  private void riverRound(GameState gameState) {
    RiverAndTurn river = new RiverAndTurn();
    river.initForRiverAndTurnGameState(gameState);
  }

  private void showdownRound(GameState gameState) {
    ShowDown showDown = new ShowDown();
    showDown.finalizeForShowDownState(gameState);
  }
}
