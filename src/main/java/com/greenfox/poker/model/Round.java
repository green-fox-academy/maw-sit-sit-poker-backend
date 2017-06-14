package com.greenfox.poker.model;


public enum Round {
  BETTING("Betting"), FLOP("Flop"), TURN("Turn"), RIVER("River"), SHOWDOWN("Showdown"), WAITING("Waiting");

  private final String round;

  private Round(String s) {
    round = s;
  }

  @Override
  public String toString() {
    return "Round{" +
            "roundName='" + round + '\'' +
            '}';
  }
}

