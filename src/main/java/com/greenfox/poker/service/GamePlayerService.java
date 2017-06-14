package com.greenfox.poker.service;


import com.greenfox.poker.model.GamePlayer;
import org.springframework.stereotype.Component;

@Component
public class GamePlayerService {


  public GamePlayer getGamePlayer(long id) {
    return mockGamePlayer();
  }

  private GamePlayer mockGamePlayer() {
    GamePlayer mockGamePlayer = new GamePlayer(1234, "andybendy", "example@gmail.com",
            null, 3100);
    return mockGamePlayer;
  }
}

