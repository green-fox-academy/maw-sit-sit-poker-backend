package com.greenfox.poker.service;

import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.repository.GameRepo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {

  @Autowired
  GameRepo gameRepo;

  HashMap<Long,GameState> gameStateList = new HashMap();

  public Game saveGame(Game game) {
    gameRepo.save(game);
    return game;
  }

  public List<Game> getAllGamesOrderedByBigBlind() {
    return gameRepo.findAllByOrderByBigBlindDesc();
  }

  public boolean isGameExist(long id) {
    if (gameRepo.exists(id)) {
      return true;
    }
    return false;
  }

  public Game getGameById(long id) {
    return gameRepo.findOne(id);
  }

  public GameState getGameState(long id) {
    return gameStateList.get(id);
  }
}