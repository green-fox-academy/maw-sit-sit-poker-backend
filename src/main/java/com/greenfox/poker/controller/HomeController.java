package com.greenfox.poker.controller;

import com.greenfox.poker.model.Deck;
import com.greenfox.poker.service.DeckService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin("*")
@Controller
public class HomeController {

  @GetMapping("/")
  @ResponseBody
  public String home() {

    DeckService deckService = new DeckService();
    Deck newDeckToTestHere = deckService.getNewDeck();
    System.out.println(newDeckToTestHere.toString());
    deckService.shuffleDeck(newDeckToTestHere);
    System.out.println(newDeckToTestHere.toString());
    return "Hello";
  }
}
