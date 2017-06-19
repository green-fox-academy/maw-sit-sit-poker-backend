package com.greenfox.poker.service;

import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GamesList;
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
  GamesList gamesList;

  public Game saveGame(Game game){
    Game newGame = new Game();
    newGame.setName(game.getName());
    newGame.setBigBlind(game.getBigBlind());
    newGame.setMaxPlayers(game.getMaxPlayers());
    newGame.setCurrentPlayers(0);
    gameRepo.save(newGame);
    return newGame;
  }

  public GamesList getAllGamesOrderedByBigBlind(){
    List<Game> temporalGameList = new ArrayList<>();
    temporalGameList = gameRepo.findAllByOrderByBigBlindDesc();
    gamesList.setGames(temporalGameList);
    return gamesList;
  }
}