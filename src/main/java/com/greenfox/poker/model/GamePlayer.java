package com.greenfox.poker.model;


  public class GamePlayer {
  private String lastAction;
  private PokerUserDTO player;
  private int bet;
  private boolean isFolded;

    public GamePlayer() {
    }

    public GamePlayer(PokerUserDTO pokerUserDTO){
      this.player = pokerUserDTO;
    }

    public String getLastAction() {
      return lastAction;
    }

    public void setLastAction(String lastAction) {
      this.lastAction = lastAction;
    }

    public PokerUserDTO getPlayer() {
      return player;
    }

    public void setPlayer(PokerUserDTO player) {
      this.player = player;
    }

    public int getBet() {
      return bet;
    }

    public void setBet(int bet) {
      this.bet = bet;
    }

    public boolean isFolded() {
      return isFolded;
    }

    public void setFolded(boolean folded) {
      isFolded = folded;
    }
  }
