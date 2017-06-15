package com.greenfox.poker.model;


public class GamePlayer {

  private Action lastAction;
  private PokerUser player;
  private int bet;
  private boolean isFolded;

  public GamePlayer() {
  }

  public GamePlayer(Action lastAction, PokerUser player, int bet, boolean isFolded) {
    this.lastAction = lastAction;
    this.player = player;
    this.bet = bet;
    this.isFolded = isFolded;
  }

  public Action getLastAction() {
    return lastAction;
  }

  public void setLastAction(Action lastAction) {
    this.lastAction = lastAction;
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
