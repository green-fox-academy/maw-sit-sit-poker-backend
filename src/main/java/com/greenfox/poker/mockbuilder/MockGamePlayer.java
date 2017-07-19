package com.greenfox.poker.mockbuilder;


import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.Rank;
import com.greenfox.poker.model.Suit;
import com.greenfox.poker.service.DeckService;
import com.greenfox.poker.service.ShowDownService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class MockGamePlayer {

  @Autowired
  ShowDownService showDownService;

  public List<Card> playerCards1 = new ArrayList<>();
  public List<Card> playerCards2 = new ArrayList<>();
  public List<Card> playerCards3 = new ArrayList<>();
  public GamePlayer gamePlayer1 = new GamePlayer();
  public GamePlayer gamePlayer2 = new GamePlayer();
  public GamePlayer gamePlayer3= new GamePlayer();

  public MockGamePlayer() {

  }

  public void fillGamePlayer() {
    DeckService deckService = new DeckService();
    Deck myDeck = deckService.getNewDeck();
    deckService.shuffleDeck(myDeck);
//    this.playerCards = new ArrayList<>(Arrays.asList(deckService.drawCardFromDeck(myDeck),
//        deckService.drawCardFromDeck(myDeck)));
 //   this.playerCards.add(deckService.drawCardFromDeck(myDeck));
 //   this.playerCards.add(deckService.drawCardFromDeck(myDeck));
    Card c1 = new Card();
    c1.setSuit(Suit.D);
    c1.setRank(Rank.ten);
    Card c2 = new Card();
    c2.setSuit(Suit.D);
    c2.setRank(Rank.jack);
    this.playerCards1.add(c1);
    this.playerCards1.add(c2);

    Card c3 = new Card();
    c3.setSuit(Suit.S);
    c3.setRank(Rank.five);
    Card c4 = new Card();
    c4.setSuit(Suit.S);
    c4.setRank(Rank.four);
    this.playerCards2.add(c3);
    this.playerCards2.add(c4);

    Card c5 = new Card();
    c5.setSuit(Suit.H);
    c5.setRank(Rank.ace);
    Card c6 = new Card();
    c6.setSuit(Suit.H);
    c6.setRank(Rank.king);
    this.playerCards3.add(c5);
    this.playerCards3.add(c6);


  //  this.playerCards = new ArrayList<>(Arrays.asList(c1, c2));
    this.gamePlayer1.setPlayersHand(this.playerCards1);
    this.gamePlayer1.setId(1L);
    this.gamePlayer2.setPlayersHand(this.playerCards2);
    this.gamePlayer2.setId(2L);
    this.gamePlayer3.setPlayersHand(this.playerCards3);
    this.gamePlayer3.setId(3L);
//    showDownService.evaluationTheHand(playerCards);

  }
}
