package com.greenfox.poker.service;

import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.GameInfoList;
import com.greenfox.poker.repository.GameRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {

  @Autowired
  GameRepo gameRepo;

  @Autowired
  GameInfoList gameInfoList;

  public Game saveGame(Game game){
    Game newGame = new Game();
    newGame.setName(game.getName());
    newGame.setBigBlind(game.getBigBlind());
    newGame.setMaxPlayers(game.getMaxPlayers());
    newGame.setCurrentPlayers(0);
    gameRepo.save(newGame);
    return newGame;
  }

  public GameInfoList getAllGamesOrderedByBigBlind(){
    List<Game> temporalGameList = new ArrayList<>();
    temporalGameList = gameRepo.findAllByOrderByBigBlindDesc();
    gameInfoList.setGames(temporalGameList);
    return gameInfoList;
  }

  public boolean isGameExist(long id){
    if (gameRepo.exists(id)) {
      return true;
    }
    return false;
  }

  public GameState getGameState(long id){
    return gameInfoList.getGameStates().get(Math.toIntExact(id));
  }
}