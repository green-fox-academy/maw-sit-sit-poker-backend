package com.greenfox.poker.model;


public enum Round {
  IDLE("idle"), BETTING("Betting"), FLOP("Flop"), TURN("Turn"), RIVER("River"), SHOWDOWN("Showdown");

  private final String roundName;

  private Round(String s) {
    roundName = s;
  }

  @Override
  public String toString() {
    return roundName;
  }
}

