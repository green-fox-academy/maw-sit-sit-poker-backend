package com.greenfox.poker.model;


import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Deck {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private List<Card> cards;

}
