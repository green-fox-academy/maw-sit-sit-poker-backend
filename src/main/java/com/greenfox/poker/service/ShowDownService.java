package com.greenfox.poker.service;


import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GamePlayerDTO;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.ShowDownResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowDownService {


  @Autowired
  GameService gameService;

  @Autowired
  GameState gameState;

  private List<Card[]> chosen5Cards = new ArrayList<>();

  public ShowDownResult whoIsTheWinner(long id) {
    List<GamePlayer> gamePlayerList = gameService.getPlayersListFromGame(id);
    GamePlayer[] gamePlayers = new GamePlayer[gamePlayerList.size()];
    List<GamePlayerDTO> gamePlayersDTO = new ArrayList<>();
    for (int i = 0; i < gamePlayerList.size(); i++) {
      gamePlayers[i] = gamePlayerList.get(i);
    }
    int[] handTypeValues = new int[gamePlayers.length];
    for (int i = 0; i < gamePlayers.length; i++) {
      List<String> playerCardsDTO = new ArrayList<>();
      List<Card> playerCards = gamePlayers[i].getPlayersHand();
      for (int j = 0; j < playerCards.size(); j++) {
        playerCardsDTO.add(gamePlayers[i].getPlayersHand().get(j).toString());
      }
      handTypeValues[i] = evaluationTheHand(playerCards);
      long userId = gamePlayers[i].getId();
      gamePlayersDTO.add(new GamePlayerDTO(userId, playerCardsDTO));
    }
    int maxHandTypeValue = Arrays.stream(handTypeValues).max().getAsInt();
    int winnerIndex = 0;
    for (int i = 0; i < handTypeValues.length; i++) {
      if (handTypeValues[i] == maxHandTypeValue) {
        winnerIndex = i;
      }
    }
    List<Long> winnerIds = new ArrayList<>();
    winnerIds.add(gamePlayers[winnerIndex].getId());
    return new ShowDownResult(winnerIds, gamePlayersDTO);
  }

  private int evaluationTheHand(List<Card> playerCards) {
    List<Card[]> allPossibility = choose5CardsOutOf7(playerCards);
    Integer[] handTypeValue = new Integer[allPossibility.size()];
    for (int i = 0; i < allPossibility.size(); i++) {
      int[] cardValue = new int[5];
      String[] cardType = new String[5];
      for (int j = 0; j < 5; j++) {
        cardValue[j] = allPossibility.get(i)[j].getRankVal();
        cardType[j] = allPossibility.get(i)[j].getSuit();
      }
      Arrays.sort(cardValue);
      handTypeValue[i] = handType(cardValue, cardType);
    }
    int maxHandTypeValue = Collections.max(Arrays.asList(handTypeValue));
    System.out.println(maxHandTypeValue);
    return maxHandTypeValue;
  }

  private List<Card> all7cards(List<Card> playerCards) {
    List<Card> all7Cards = new ArrayList<>();
    all7Cards.addAll(playerCards);
    all7Cards.addAll(gameState.getCardsOnTable());
    return all7Cards;
  }

  private List<Card[]> choose5CardsOutOf7(List<Card> playerCards) {
    List<Card> allSevenCards = all7cards(playerCards);
    Card[] sevenCard = new Card[7];
    for (int i = 0; i < sevenCard.length; i++) {
      sevenCard[i] = allSevenCards.get(i);
    }
    Card[] chosenCardsArray = new Card[5];
    chosen5Cards.clear();
    combinationsOf5(sevenCard, 5, 0, chosenCardsArray);
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

  private int handType(int[] cardValue, String[] cardType) {
    int handType = 0;
    int pairIndex = -1;
    //check for pair
    for (int j = 0; j < 4; j++) {
      if (cardValue[j] == cardValue[j + 1]) {
        pairIndex = j;
        handType = 1;
        j = 4;
        System.out.println("It is Pair, HandType = 1");
      }
    }
    //check for 2 pair
    if (handType == 1) {
      for (int j = pairIndex + 2; j < 4; j++) {
        if (cardValue[j] == cardValue[j + 1]) {
          handType = 2;
          System.out.println("It is 2 pair, HandType = 2");
        }
      }
    }

    //check for 3 of a kind or full house
    for (int i = 0; i < 3; i++) {
      if (cardValue[i] == cardValue[i + 1] && cardValue[i + 1] == cardValue[i + 2]) {
        handType = 3;
        System.out.println("It is 3 of Kind, HandType = 3");
        if (i == 0 && cardValue[3] == cardValue[4] || i == 2 && cardValue[0] == cardValue[1]) {
          handType = 6;
          System.out.println("It is FullHouse, HandType = 6");
        }
      }
    }

    //check for 4 of a kind
    for (int i = 0; i < 2; i++) {
      if (cardValue[i] == cardValue[i + 1] && cardValue[i + 1] == cardValue[i + 2] &&
          cardValue[i + 2] == cardValue[i + 3]) {
        handType = 7;
        System.out.println("It is 4 Kind, HandType = 7");
      }
    }

    //check for straight (if we haven't already found any pairs)
    if (handType == 0) {
      if ((cardValue[4] - cardValue[0] == 4) ||
          (cardValue[3] - cardValue[0] == 3 && cardValue[4] == 14 && cardValue[0] == 2)) {
        handType = 4;
        System.out.println("It is Straight, HandType = 4");
      }
    }

    //check for flush (if we haven't already found any pairs)
    boolean flush;
    if (handType == 0 || handType == 4) {
      flush = true;
      for (int i = 0; i < 4; i++) {
        if (!cardType[i].contains(cardType[i + 1])) {
          flush = false;
        }
      }
      if (flush && handType == 4) {
        handType = 8; //straight flush!
        System.out.println("It is Straight Flush, HandType = 4");
      } else if (flush) {
        handType = 5;
        System.out.println("It is Flush, HandType = 5");
      }
    }

    //check for royal flush (if it's a straight flush)
    if (handType == 8 && cardValue[4] == 14 && cardValue[0] == 10) {
      handType = 9; //royal flush!
      System.out.println("It is Royal Flush, HandType = 9");
    }
    return handType;
  }
}


