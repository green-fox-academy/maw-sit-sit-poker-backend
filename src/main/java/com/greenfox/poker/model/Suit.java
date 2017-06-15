package com.greenfox.poker.model;


public enum Suit {
  S("S"), H("H"), D("D"), C("C");

  private final String suitName;

  private Suit(String s) {
    suitName = s;
  }

  @Override
  public String toString() {
    return suitName;
  }
}