package com.greenfox.poker.service;


import com.greenfox.poker.gamestates.Betting;
import com.greenfox.poker.gamestates.BettingCycleInEachRound;
import com.greenfox.poker.gamestates.Flop;
import com.greenfox.poker.gamestates.River;
import com.greenfox.poker.gamestates.ShowDown;
import com.greenfox.poker.gamestates.Turn;
import org.springframework.beans.factory.annotation.Autowired;

public class GameStateService {

  GameService gameService;
  BettingCycleInEachRound bettingCycleInEachRound = new BettingCycleInEachRound();

  @Autowired
  public GameStateService(GameService gameService) {
    this.gameService = gameService;
  }

  public void oneFullRoundOfPokerGame(long gameStateId){
    bettingRound(gameStateId);
    flopRound(gameStateId);
    turnRound(gameStateId);
    riverRound(gameStateId);
    showdownRound(gameStateId);
  }

  private void bettingRound(long gameStateId){
    Betting betting = new Betting(gameService);
    betting.initForBettingGameState(gameStateId);
    bettingCycleInEachRound.bettingInBettingState(gameStateId);
  }

  private void flopRound(long gameStateId){
    Flop flop = new Flop(gameService);
    flop.initForBettingGameState(gameStateId);
    bettingCycleInEachRound.bettingInFlopTurnRiverShowdownState(gameStateId);
  }

  private void turnRound(long gameStateId) {
    Turn turn  = new Turn(gameService);
    turn.initForTurnGameState(gameStateId);
    bettingCycleInEachRound.bettingInFlopTurnRiverShowdownState(gameStateId);
  }

  private void riverRound(long gameStateId) {
    River river = new River(gameService);
    river.initForRiverGameState(gameStateId);
    bettingCycleInEachRound.bettingInFlopTurnRiverShowdownState(gameStateId);
  }

  private void showdownRound(long gameStateId) {
    ShowDown showDown = new ShowDown(gameService);
    bettingCycleInEachRound.bettingInFlopTurnRiverShowdownState(gameStateId);
    showDown.finalizeForShowDownState(gameStateId);
  }
}
