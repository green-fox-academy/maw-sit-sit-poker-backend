package com.greenfox.poker.service;


import com.greenfox.poker.gamestates.Betting;
import com.greenfox.poker.gamestates.Flop;
import com.greenfox.poker.gamestates.RiverAndTurn;
import com.greenfox.poker.gamestates.ShowDown;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GameState;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class GameStateService {

  private final static Logger logger = Logger.getLogger(GameStateService.class.getName());

  GameService gameService;

  @Autowired
  public GameStateService(GameService gameService) {
    this.gameService = gameService;
  }


  private void bettingRound(GameState gameState){
    logger.log(Level.INFO, "entering round: Betting");
    Betting betting = new Betting();
    Game game = gameService.getGameById(gameState.getId());
    int bigBlindAmount = game.getBigBlind();
    betting.initForBettingGameState(gameState, bigBlindAmount);
  }

  private void flopRound(GameState gameState){
    logger.log(Level.INFO, "entering round: Flop");
    Flop flop = new Flop();
    flop.initForFlopGameState(gameState);
  }

  private void turnRound(GameState gameState) {
    logger.log(Level.INFO, "entering round: Turn");
    RiverAndTurn turn  = new RiverAndTurn();
    turn.initForRiverAndTurnGameState(gameState);
  }

  private void riverRound(GameState gameState) {
    logger.log(Level.INFO, "entering round: River");
    RiverAndTurn river = new RiverAndTurn();
    river.initForRiverAndTurnGameState(gameState);
  }

  private void showdownRound(GameState gameState) {
    logger.log(Level.INFO, "entering round: ShowDown");
    ShowDown showDown = new ShowDown();
    showDown.finalizeForShowDownState(gameState);
  }
}
