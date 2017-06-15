package com.greenfox.poker.model;


public class Card {

  private Suit suit;
  private Rank rank;


  public Card() {
  }

  public Suit getSuit() {
    return suit;
  }

  public void setSuit(Suit suit) {
    this.suit = suit;
  }

  public Rank getRank() {
    return rank;
  }

  public void setRank(Rank rank) {
    this.rank = rank;
  }

  @Override
  public String toString() {
    return suit.toString() + rank.toString();
  }
}

