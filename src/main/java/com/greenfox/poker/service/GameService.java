package com.greenfox.poker.service;

import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.repository.GameRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {

  @Autowired
  GameRepo gameRepo;

  List<GameState> gameStateList = new ArrayList<>();

  public Game saveGame(Game game){
    Game newGame = new Game();
    newGame.setName(game.getName());
    newGame.setBigBlind(game.getBigBlind());
    newGame.setMaxPlayers(game.getMaxPlayers());
    newGame.setCurrentPlayers(0);
    gameRepo.save(newGame);
    return newGame;
  }

  public List<Game> getAllGamesOrderedByBigBlind(){
    return gameRepo.findAllByOrderByBigBlindDesc();
  }

  public boolean isGameExist(long id){
    if (gameRepo.exists(id)) {
      return true;
    }
    return false;
  }

  public Game getGamebyId(long id){
    return gameRepo.findOne(id);
  }

  public GameState getGameState(long id) {
    for (GameState gameState : gameStateList) {
      if (id == gameState.getId()) {
        return gameState;
      }
    }
    return new GameState();
  }
}