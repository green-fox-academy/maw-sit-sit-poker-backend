package com.greenfox.poker.mockbuilder;


import com.greenfox.poker.model.Game;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MockGameBuilder {

  private Game mockGame;

  public MockGameBuilder(){
    mockGame = new Game("Table", 20, 3);
    mockGame.setId(1l);
  }

  public void setMockGame(Game mockGame) {
    this.mockGame = mockGame;
  }

  public MockGameBuilder setName(String name){
    mockGame.setName(name);
    return this;
  }

  public MockGameBuilder setBigBlind(int blind){
    mockGame.setBigBlind(blind);
    return this;
  }

  public MockGameBuilder setMAxPlayer(int max){
    mockGame.setMaxPlayers(max);
    return this;
  }

  public Game build(){
    return mockGame;
  }

  public List<Game> createListOfAllGamesOrderedByBigBlind () {
    List<Game> topTenGames = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      topTenGames.add(build());
    }
    return topTenGames;
  }
}
