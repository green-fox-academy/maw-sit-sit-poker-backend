package com.greenfox.poker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.concurrent.atomic.AtomicInteger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

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

  @JsonProperty("current_players")
  private Integer currentPlayers;

  public Game() {
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


  public int getCurrentPlayers() {
    return currentPlayers;
  }

  public void setCurrentPlayers(Integer currentPlayers) {
    this.currentPlayers = currentPlayers;
  }
}


