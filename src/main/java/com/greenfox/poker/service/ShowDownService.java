package com.greenfox.poker.service;


import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import com.greenfox.poker.model.GamePlayerHand;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.Rank;
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

  private static List<Card[]> chosen5Cards = new ArrayList<>();

  private List<Card> all7cards(List<Card> playerCards) {
    List<Card> all7Cards = new ArrayList<>();
       all7Cards.addAll(playerCards);
    //   all7Cards.addAll(gameState.getCardsOnTable());
    DeckService deckService = new DeckService();
    Deck myDeck = deckService.getNewDeck();
    deckService.shuffleDeck(myDeck);
    all7Cards.addAll(new ArrayList<>(Arrays.asList(deckService.drawCardFromDeck(myDeck),
        deckService.drawCardFromDeck(myDeck),
        deckService.drawCardFromDeck(myDeck),
        deckService.drawCardFromDeck(myDeck),
        deckService.drawCardFromDeck(myDeck))));
    for (Card item: all7Cards) {
      System.out.println(item.getRankVal());

    }
    return all7Cards;
  }

  public List<Card[]> choose5CardsOutOf7(List<Card> playerCards) {
    List<Card> allSevenCards = all7cards(playerCards);
    Card[] sevenCard = new Card[7];
    for (int i = 0; i < sevenCard.length; i++) {
      sevenCard[i] = allSevenCards.get(i);
    }
    Card[] chosenCardsArray = new Card[5];
    combinationsOf5(sevenCard, 5, 0, chosenCardsArray);
    for (Card[] item : chosen5Cards) {
      //    System.out.println(item[0] + " " + item[1] + " " + item[2] + " " + item[3] + " " + item[4]);
      System.out.println(Arrays.toString(item));
      System.out.println(item[2].getSuit() + "  " + item[2].getRank());
    }
    System.out.println(chosen5Cards.size());
    return chosen5Cards;
  }

  private void combinationsOf5(Card[] input, int len, int startPosition, Card[] result) {
    if (len == 0) {
      chosen5Cards.add(Arrays.copyOf(result, 5));
      return;
    }
    for (int i = startPosition; i <= input.length - len; i++) {
      result[result.length - len] = input[i];
      combinationsOf5(input, len - 1, i + 1, result);
    }
  }



}


