package com.greenfox.poker.service;


import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import com.greenfox.poker.model.GamePlayerHand;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.Rank;
import com.greenfox.poker.model.Suit;
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
    Card c1 = new Card(); c1.setSuit(Suit.H); c1.setRank(Rank.queen);
    Card c2 = new Card(); c2.setSuit(Suit.H); c2.setRank(Rank.jack);
    Card c3 = new Card(); c3.setSuit(Suit.H); c3.setRank(Rank.ten);
    Card c4 = new Card(); c4.setSuit(Suit.C); c4.setRank(Rank.nine);
    Card c5 = new Card(); c5.setSuit(Suit.D); c5.setRank(Rank.eight);
    all7Cards.addAll(new ArrayList<>(Arrays.asList(c1,c2, c3, c4,c5)));
//    all7Cards.addAll(new ArrayList<>(Arrays.asList(deckService.drawCardFromDeck(myDeck),
//        deckService.drawCardFromDeck(myDeck),
//        deckService.drawCardFromDeck(myDeck),
//        deckService.drawCardFromDeck(myDeck),
//        deckService.drawCardFromDeck(myDeck))));

    for (Card item : all7Cards) {
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
      //     System.out.println(item[2].getSuit() + "  " + item[2].getRank());
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

  public void evaluationTheHand(List<Card> playerCards) {
    List<Card[]> allPossibility = choose5CardsOutOf7(playerCards);

    for (int i = 0; i < allPossibility.size(); i++) {
      int[] cardValue = new int[5];
      String[] cardType = new String[5];
      for (int j = 0; j < 5; j++) {
        cardValue[j] = allPossibility.get(i)[j].getRankVal();
        cardType[j] = allPossibility.get(i)[j].getSuit();
      }
      Arrays.sort(cardValue);
      System.out.println((Arrays.toString(allPossibility.get(i))));
      System.out.println(Arrays.toString(cardValue));

      handType(cardValue, cardType);

    }

  }

  private int handType(int[] cardValue, String[] cardType) {
    int handType = 0;
    int pairIndex = -1;
    //check for pair
    for (int j = 0; j < 4; j++) {
      if (cardValue[j] == cardValue[j + 1]) {
        pairIndex = j;
        handType = 1;
        j = 4;
        System.out.println("IT IS Pair, HandType = 1");
      }
    }
    //check for 2 pair
    if (handType == 1) {
      for (int j = pairIndex + 2; j < 4; j++) {
        if (cardValue[j] == cardValue[j + 1]) {
          handType = 2;
          System.out.println("IT IS 2 pair, HandType = 2");
        }
      }
    }

    //check for 3 of a kind or full house
    for (int i = 0; i < 3; i++) {
      if (cardValue[i] == cardValue[i + 1] && cardValue[i + 1] == cardValue[i + 2]) {
        handType = 3;
        System.out.println("IT 3 of Kind, HandType = 3");
        if (i == 0 && cardValue[3] == cardValue[4] || i == 2 && cardValue[0] == cardValue[1]) {
          handType = 6;
        }
        System.out.println("IT FullHouse, HandType = 6");
      }
    }

    //check for 4 of a kind
    for(int i = 0; i < 2; i++)
      if(cardValue[i] == cardValue[i + 1] && cardValue[i + 1] == cardValue[i + 2] &&
          cardValue[i + 2] == cardValue[i + 3]) {
        handType = 7;
        System.out.println("IT 4 Kind, HandType = 7");
      }

    //check for straight (if we haven't already found any pairs)
    if(handType == 0)
      if((cardValue[4] - cardValue[0] == 4) ||
          (cardValue[3] - cardValue[0] == 3 && cardValue[4] == 14 && cardValue[0] == 2)) {
        handType = 4;
        System.out.println("IT Straight, HandType = 4");
      }

    //check for flush (if we haven't already found any pairs)
    boolean flush;
    if(handType == 0 || handType == 4) {
      flush = true;
      for(int i = 0; i < 4; i++)
        if(!cardType[i].contains(cardType[i+1]))
          flush = false;
      if(flush && handType == 4) {
        handType = 8; //straight flush!
        System.out.println("IT Straight Flush, HandType = 4");
      }else if(flush) {
        handType = 5;
        System.out.println("IT Flush, HandType = 5");
      }
    }

    //check for royal flush (if it's a straight flush)
    if(handType == 8 && cardValue[4] == 14 && cardValue[0] == 10) {
      handType = 9; //royal flush!
      System.out.println("IT Royal Flush, HandType = 9");
    }
    return handType;
  }

}


