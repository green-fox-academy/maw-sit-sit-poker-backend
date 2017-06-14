package com.greenfox.poker.model;


public enum Round {
  BETTING("Betting"), FLOP("Flop"), TURN("Turn"), RIVER("River"), SHOWDOWN("Showdown"), WAITING("Waiting");

  private final String name;

  private Round(String s) {
    name = s;
  }
}

