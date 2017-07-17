package com.greenfox.poker.model;


public enum Rank {
  two("2"), three("3"), four("4"), five("5"), six("6"), seven("7"), eight("8"), nine("9"), ten(
          "T"), jack("J"), queen("Q"), king("K"), ace("A");

  private final String rankName;

  private Rank(String s) {
    rankName = s;
  }

  @Override
  public String toString() {
    return rankName;
  }
}