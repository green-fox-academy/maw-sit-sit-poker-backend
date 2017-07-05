package com.greenfox.poker.gamestates;

import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.service.DeckService;
import com.greenfox.poker.service.GameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


public class River {

  GameService gameService;

  @Autowired
  public River(GameService gameService) {
    this.gameService = gameService;
  }

  public void initForRiverGameState(long gameStateId) {
    getRiverCard(gameStateId);
  }

  private void getRiverCard(long gameStateId) {
    DeckService deckService = new DeckService();
    GameState gameState = gameService.getGameStates().get(gameStateId);
    Deck deckToDealFrom = gameState.getDeckInGameState();
    List<Card> riverCards = gameState.getCardsOnTable();
    Card cardToDrawn = deckService.drawCardFromDeck(deckToDealFrom);
    riverCards.add(cardToDrawn);
    gameState.setCardsOnTable(riverCards);
  }
}
