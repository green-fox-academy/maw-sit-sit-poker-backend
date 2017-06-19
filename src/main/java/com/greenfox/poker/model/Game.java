package com.greenfox.poker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String name;
  private int bigBlind;
  private int maxPlayers;
  private int currentPlayers;

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

  public void setBigBlind(int bigBlind) {
    this.bigBlind = bigBlind;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public void setMaxPlayers(int maxPlayers) {
    this.maxPlayers = maxPlayers;
  }

  public int getCurrentPlayers() {
    return currentPlayers;
  }

  public void setCurrentPlayers(int currentPlayers) {
    this.currentPlayers = currentPlayers;
  }
}


