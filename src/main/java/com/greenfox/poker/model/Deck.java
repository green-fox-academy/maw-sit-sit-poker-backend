package com.greenfox.poker.model;

import java.util.List;


public class Deck {

  private long id;
  private List<Card> cards;

  public Deck() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  @Override
  public String toString() {
    return "Deck{" +
            "id=" + id +
            ", cards=" + cards +
            '}';
  }
}

