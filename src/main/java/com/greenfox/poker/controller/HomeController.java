package com.greenfox.poker.controller;

import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import com.greenfox.poker.model.Rank;
import com.greenfox.poker.model.Suit;
import com.greenfox.poker.service.Accessible;

import com.greenfox.poker.service.DeckService;
import com.greenfox.poker.service.ShowDownService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin("*")
@Controller
public class HomeController {

  @Autowired
  ShowDownService showDownService;

  @GetMapping("/")
  @ResponseBody
  @Accessible
  public String home() {
    DeckService deckService = new DeckService();
    Deck myDeck = deckService.getNewDeck();
    deckService.shuffleDeck(myDeck);
  //  List<Card> playerCards = new ArrayList<>(Arrays.asList(deckService.drawCardFromDeck(myDeck),
  //      deckService.drawCardFromDeck(myDeck)));
    Card c1 = new Card();
    c1.setSuit(Suit.H);
    c1.setRank(Rank.ace);
    Card c2 = new Card();
    c2.setSuit(Suit.H);
    c2.setRank(Rank.king);
    List<Card> playerCards = new ArrayList<>(Arrays.asList(c1,c2));

    showDownService.evaluationTheHand(playerCards);
    return "Hello";
  }
}
