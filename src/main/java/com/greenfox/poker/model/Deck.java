package com.greenfox.poker.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Deck {

  private long id;
  private List<Card> cards;
  private static final AtomicInteger totalDeckCounter = new AtomicInteger(0);



  public Deck() {
    id = totalDeckCounter.incrementAndGet();
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

