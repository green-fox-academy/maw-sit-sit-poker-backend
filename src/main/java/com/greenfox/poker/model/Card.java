package com.greenfox.poker.model;


public class Card {

  private Suit suit;
  private Rank rank;

  public Card() {
  }

  public String getSuit() {
    return suit.toString();
  }

  public void setSuit(Suit suit) {
    this.suit = suit;
  }

  public String getRank() {
    return rank.toString();
  }

  public int getRankVal() {
    return rank.getRankValue();
  }

  public void setRank(Rank rank) {
    this.rank = rank;
  }

  @Override
  public String toString() {
    return suit.toString().concat(rank.toString());
  }

  public String printCard(){
    return (suit.toString()+rank.getRankValue());
  }
}

