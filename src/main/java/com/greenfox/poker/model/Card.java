package com.greenfox.poker.model;

import org.springframework.stereotype.Component;

@Component
public class Card {

  private String suit;
  private String rank;

  public Card() {
  }

  public String getSuit() {
    return suit;
  }

  public void setSuit(Suit suit) {
    this.suit = suit.toString();
  }

  public String getRank() {
    return rank;
  }

  public void setRank(Rank rank) {
    this.rank = rank.toString();
  }

  @Override
  public String toString() {
    return suit.concat(rank);
  }
}

