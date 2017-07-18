package com.greenfox.poker.service;


import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import com.greenfox.poker.model.GamePlayerHand;
import com.greenfox.poker.model.GameState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowDownService {

  @Autowired
  GamePlayerHand gamePlayerHand;

  @Autowired
  GameState gameState;


  private List<Card> all7cards() {
    List<Card> all7Cards = new ArrayList<>();
    //   all7Cards.addAll(gamePlayerHand.getPlayerCards());
    //   all7Cards.addAll(gameState.getCardsOnTable());
    DeckService deckService = new DeckService();
    Deck myDeck = deckService.getNewDeck();
    deckService.shuffleDeck(myDeck);
    all7Cards.addAll(new ArrayList<>(Arrays.asList(deckService.drawCardFromDeck(myDeck),
        deckService.drawCardFromDeck(myDeck),
        deckService.drawCardFromDeck(myDeck),
        deckService.drawCardFromDeck(myDeck),
        deckService.drawCardFromDeck(myDeck))));
    deckService.shuffleDeck(myDeck);
    all7Cards.addAll(new ArrayList<>(Arrays.asList(deckService.drawCardFromDeck(myDeck),
        deckService.drawCardFromDeck(myDeck))));
    return all7Cards;
  }

  public List<Card> choose5CardsOutOf7() {
    List<Card> allSevenCards = all7cards();
    Card[] sevenCard = new Card[7];
    for (int i = 0; i < sevenCard.length; i++) {
      sevenCard[i] = allSevenCards.get(i);
      System.out.println(allSevenCards.get(i));
      System.out.println("###############");
      System.out.println(sevenCard[i]);
    }
    Card[] chosenCardsArray = new Card[5];
    combinationsOf5(sevenCard, 5, 0, chosenCardsArray);
    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxx");
    for (Card[] item : resultList) {
  //    System.out.println(item[0] + " " + item[1] + " " + item[2] + " " + item[3] + " " + item[4]);
      System.out.println(Arrays.toString(item));

    }
    List<Card> chosenCards = new ArrayList<>();

    System.out.println(resultList.size());
    return chosenCards;
  }

  private static List<Card[]> resultList = new ArrayList<>();

  private void combinationsOf5(Card[] input, int len, int startPosition, Card[] result) {
    if (len == 0) {
      resultList.add(Arrays.copyOf(result,5));
      System.out.println(Arrays.toString(result));
      return;
    }
    for (int i = startPosition; i <= input.length - len; i++) {
      result[result.length - len] = input[i];
      combinationsOf5(input, len - 1, i + 1, result);
    }
  }
}


