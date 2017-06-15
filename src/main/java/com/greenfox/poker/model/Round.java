package com.greenfox.poker.model;


public enum Round {
  BETTING("Betting"), FLOP("Flop"), TURN("Turn"), RIVER("River"), SHOWDOWN("Showdown"), WAITING("Waiting");

  private final String roundName;

  private Round(String s) {
    roundName = s;
  }

  @Override
  public String toString() {
    return roundName;
  }
}

