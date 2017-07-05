package com.greenfox.poker.gamestates;


import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.Round;
import com.greenfox.poker.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

public class Idle {

  GameService gameService;

  @Autowired
  public Idle(GameService gameService) {
    this.gameService = gameService;
  }

  public void checkForPlayersNumberToStartTheGame() {
    for (GameState gameStateValue : gameService.getGameStates().values()) {
      if (gameStateValue.getRound() == Round.IDLE) {
        if (gameStateValue.getPlayers().size() >= 2) {
          gameStateValue.setRound(Round.BETTING);
        }
      }
    }
  }
}
