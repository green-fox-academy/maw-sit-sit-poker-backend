package com.greenfox.poker.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;


@Component
public class Deck {

  private static AtomicInteger nextId = new AtomicInteger(0);

  private long id;
  private List<Card> cards;

  public Deck() {
    id = nextId.incrementAndGet();
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
}