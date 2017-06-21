package com.greenfox.poker.service;

import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import com.greenfox.poker.model.Rank;
import com.greenfox.poker.model.Suit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;


public class DeckService {

  private static AtomicLong nextId = new AtomicLong(0);

  public Deck getNewDeck(){
    Deck newDeck = new Deck();
    newDeck.setId(nextId.incrementAndGet());
    newDeck.setCards(setNewDeckCards());
    return newDeck;
  }

  private List<Card> setNewDeckCards() {
    List<Card> newDeckCardList = new ArrayList<>();
    for (int i = 0; i < 13 ; i++) {
      newDeckCardList.add(getOneCardForDeckBuilding(Suit.C, i));
      newDeckCardList.add(getOneCardForDeckBuilding(Suit.D, i));
      newDeckCardList.add(getOneCardForDeckBuilding(Suit.H, i));
      newDeckCardList.add(getOneCardForDeckBuilding(Suit.S, i));
    }
    return newDeckCardList;
  }

  private Card getOneCardForDeckBuilding(Suit thisSuit, int i) {
    Card newCard = new Card();
    newCard.setSuit(thisSuit);
    newCard.setRank(Rank.values()[i]);
    return newCard;
  }

  public Deck shuffleDeck(Deck deck){
    List<Card> cardsToShuffle = deck.getCards();
    Collections.shuffle(cardsToShuffle);
    deck.setCards(cardsToShuffle);
    return deck;
  }

  public Card returnRandomCard(Deck deck){
    Card randomCard;
    List<Card> cardsFromDeck = deck.getCards();
    int randomCardId = new Random().nextInt(cardsFromDeck.size()) + 1;
    randomCard = cardsFromDeck.get(randomCardId);
    return randomCard;
  }

}
