package com.greenfox.poker.gamestates;


import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.service.DeckService;
import com.greenfox.poker.service.GameService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class Flop {

  public Flop() {
  }

  public void initForBettingGameState(GameState gameState) {
    DeckService deckService = new DeckService();
    List<Card> flopCards = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      Card cardToDrawn = deckService.drawCardFromDeck(gameState.getDeckInGameState());
      flopCards.add(cardToDrawn);
    }
    gameState.setCardsOnTable(flopCards);
  }
}
