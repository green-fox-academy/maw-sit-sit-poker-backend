package com.greenfox.poker.service;

import com.greenfox.poker.model.Game;
import com.greenfox.poker.repository.GameRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {

  @Autowired
  GameRepo gameRepo;

  public void saveGame(Game game){
    gameRepo.save(game);
  }

  public List<Game> getAllGamesOrderedByBigBlind(){
    return gameRepo.findAllByOrderByBigBlindDesc();
  }
}
