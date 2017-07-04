package com.greenfox.poker.gamestates;

import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.service.DeckService;
import java.util.List;


public class RiverAndTurn {

  public RiverAndTurn() {
  }

  public void initForRiverAndTurnGameState(GameState gameState) {
    DeckService deckService = new DeckService();
    List<Card> cardsOnTable = gameState.getCardsOnTable();
    Card cardToDrawn = deckService.drawCardFromDeck(gameState.getDeckInGameState());
    cardsOnTable.add(cardToDrawn);
    gameState.setCardsOnTable(cardsOnTable);
  }
}


