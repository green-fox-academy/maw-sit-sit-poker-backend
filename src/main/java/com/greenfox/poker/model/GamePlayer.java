package com.greenfox.poker.model;


public class GamePlayer extends PokerUserDTO {

  private Action lastAction;
  private int bet;
  private boolean isFolded;
  private boolean isActive;

  public GamePlayer() {
  }

  public GamePlayer(long chips, PokerUserDTO pokerUserDTO) {
    this.setId(pokerUserDTO.getId());
    this.setUsername(pokerUserDTO.getUsername());
    this.setAvatar(pokerUserDTO.getAvatar());
    this.setChips(chips);
    this.bet = 0;
  }

  public Action getLastAction() {
    return lastAction;
  }

  public void setLastAction(Action lastAction) {
    this.lastAction = lastAction;
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
