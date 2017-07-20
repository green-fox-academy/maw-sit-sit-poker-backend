package com.greenfox.poker.model;


public enum Round {
  IDLE("idle"), BETTING("betting"), FLOP("flop"), TURN("turn"), RIVER("river"), SHOWDOWN("showdown");

  public final String roundName;

  private Round(String s) {
    roundName = s;
  }

  @Override
  public String toString() {
    return roundName;
  }
}

