package com.greenfox.poker.model;


  public class GamePlayer {
  private String lastAction;
  private PokerUser player;
  private int bet;
  private boolean isFolded;

  public GamePlayer() {
  }

    public String getLasAction() {
      return lastAction;
    }

    public void setLasAction(Action action) {
      this.lastAction = action.toString();
    }

    public PokerUser getPlayer() {
    return player;
  }

  public void setPlayer(PokerUser player) {
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
