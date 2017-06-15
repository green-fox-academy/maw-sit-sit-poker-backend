package com.greenfox.poker.service;


import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.repository.GamePlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GamePlayerService {

  @Autowired
  GamePlayerRepository gamePlayerRepository;


  public GamePlayer getGamePlayer(long id) {
    return mockGamePlayer();
  }

  private GamePlayer mockGamePlayer() {
    GamePlayer mockGamePlayer = new GamePlayer(1234, "andybendy", "example@gmail.com",
            null, 3100);
    gamePlayerRepository.save(mockGamePlayer);
    GamePlayer mockGPToReturn = gamePlayerRepository.findOne(1234l);
    return mockGPToReturn;
  }
}

