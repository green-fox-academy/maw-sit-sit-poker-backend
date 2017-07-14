package com.greenfox.poker.service;


import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.GamePlayerHand;
import com.greenfox.poker.model.GameState;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowDownService {

  @Autowired
  GamePlayerHand gamePlayerHand;
  
  @Autowired
  GameState gameState;

  public List<Card> all7cards() {
    List<Card> all7Cards = new ArrayList<>();
    all7Cards.addAll(gamePlayerHand.getPlayerCards());
    all7Cards.addAll(gameState.getCardsOnTable());
    return all7Cards;
  }

  public List<Card> choose5CardsOutOf7() {
    List<Card> chosenCards = new ArrayList<>();

    return chosenCards;
  }
}
