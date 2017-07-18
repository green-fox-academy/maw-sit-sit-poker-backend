package com.greenfox.poker.model;


public enum Rank {
  two("2", 2), three("3", 3), four("4", 4), five("5", 5), six("6", 6), seven("7", 7), eight("8", 8), nine("9", 9), ten(
      "T", 10), jack("J", 11), queen("D", 12), king("K", 13), ace("A", 14);


  private final String rankName;
  private final int rankValue;

  private Rank(String s, int rankValue) {
    rankName = s;
    this.rankValue = rankValue;
  }

  @Override
  public String toString() {
    return rankName;
  }

  public int getRankValue(){
    return rankValue;
  }
}