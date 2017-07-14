package com.greenfox.poker.model;


public enum HandType {

  ROYAL_FLUSH("RoyalFlush"), STRAIGHT_FLUSH("StraightFlush"), FOUR_OF_A_KIND("FourOfAKind"), FULL_HOUSE("FullHouse"),
  FLUSH("Flush"), STRAIGHT("Straight"), THREE_OF_A_KIND("ThreeOfAKind"), TWO_PAIRS("TwoPairs"), ONE_PAIR("OnePair"),
  HIGH_CARD("HighCard");

  private final String showDownRankName;

  HandType(String s) {
    this.showDownRankName = s;
  }

  @Override
  public String toString() {
    return showDownRankName;
  }
}
