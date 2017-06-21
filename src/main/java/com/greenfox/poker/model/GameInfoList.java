package com.greenfox.poker.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GameInfoList {

  private List<Game> games;

  @JsonIgnore
  private List<GameState> gameStates;


  public GameInfoList() {
  }

  public List<Game> getGames() {
    return games;
  }

  public void setGames(List<Game> games) {
    this.games = games;
  }

  public List<GameState> getGameStates() {
    return gameStates;
  }

  public void setGameStates(List<GameState> gameStates) {
    this.gameStates = gameStates;
  }
}
