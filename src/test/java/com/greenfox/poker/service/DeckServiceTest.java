package com.greenfox.poker.service;

import static org.junit.Assert.*;

import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;


public class DeckServiceTest {

  private Deck testDeck;
  private List<Card> cardsInTestDeck;
  private List<String> validCardList;


  private void createTestDeckAndCardsList() {
    DeckService deckService = new DeckService();
    testDeck = deckService.getNewDeck();
    cardsInTestDeck = testDeck.getCards();
    validCardList = Arrays
            .asList("C2", "D2", "H2", "S2", "C3", "D3", "H3", "S3", "C4", "D4", "H4", "S4", "C5",
                    "D5", "H5", "S5", "C6", "D6", "H6", "S6", "C7", "D7", "H7", "S7", "C8", "D8",
                    "H8", "S8", "C9", "D9", "H9", "S9", "CT", "DT", "HT", "ST", "CJ", "DJ", "HJ",
                    "SJ", "CD", "DD", "HD", "SD", "CK", "DK", "HK", "SK", "CA", "DA", "HA", "SA"
            );
  }

  private void setDeckAndCardsListToNull() {
    testDeck = null;
    cardsInTestDeck = null;
  }


  @Test
  public void TestIfDeckContainsTheRightAmountOfCards() throws Exception {
    createTestDeckAndCardsList();
    assertEquals(52, cardsInTestDeck.size());
    setDeckAndCardsListToNull();
  }

  public void TestIfAllCardsAreDifferent() throws Exception {
    createTestDeckAndCardsList();

    setDeckAndCardsListToNull();
  }

}