package com.greenfox.poker.gamestates;


import com.greenfox.poker.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowDown {

  private GameService gameService;

  @Autowired
  public ShowDown(GameService gameService) {
    this.gameService = gameService;
  }

  public void finalizeForShowDownState(long gameStateId) {

  }
}
