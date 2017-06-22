package com.greenfox.poker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  private String name;

  @NotNull
  @JsonProperty("big_blind")
  private Integer bigBlind;

  @NotNull
  @JsonProperty("max_players")
  private Integer maxPlayers;

  private long gamestate_id;


  public Game() {
  }

  public long getGamestate_id() {
    return gamestate_id;
  }

  public void setGamestate_id(long gamestate_id) {
    this.gamestate_id = gamestate_id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public int getBigBlind() {
    return bigBlind;
  }

  public void setBigBlind(Integer bigBlind) {
    this.bigBlind = bigBlind;
  }


  public int getMaxPlayers() {
    return maxPlayers;
  }

  public void setMaxPlayers(Integer maxPlayers) {
    this.maxPlayers = maxPlayers;
  }

}


