package com.greenfox.poker.gamestates;


import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.Round;
import com.greenfox.poker.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

public class Waiting {

  GameService gameService;

  @Autowired
  public Waiting(GameService gameService) {
    this.gameService = gameService;
  }

  public void checkForPlayersNumberToStartTheGame(GameState gameState) {
    for (GameState gameStateValue : gameService.getGameStateMap().values()) {
      if (gameStateValue.getRound() == Round.WAITING) {
        if (gameStateValue.getPlayers().size() >= 2) {
          gameStateValue.setRound(Round.BETTING);
        }
      }
    }
  }
}
