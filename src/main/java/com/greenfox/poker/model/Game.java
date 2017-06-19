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
  private int maxPlayersNum;
  private Integer currentPlayersNum;


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

  public int getMaxPlayersNum() {
    return maxPlayersNum;
  }

  public void setMaxPlayersNum(int maxPlayersNum) {
    this.maxPlayersNum = maxPlayersNum;
  }

  public int getCurrentPlayersNum() {
    return currentPlayersNum;
  }

  public void setCurrentPlayersNum(int currentPlayersNum) {
    this.currentPlayersNum = currentPlayersNum;
  }
}


