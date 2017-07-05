package com.greenfox.poker.gamestates;


import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.service.DeckService;
import com.greenfox.poker.service.GameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class Turn {

  GameService gameService;

  @Autowired
  public Turn(GameService gameService) {
    this.gameService = gameService;
  }

  public void initForTurnGameState(long gameStateId) {
    getTurnCard(gameStateId);
  }

  private void getTurnCard(long gameStateId) {
    DeckService deckService = new DeckService();
    GameState gameState = gameService.getGameStates().get(gameStateId);
    Deck deckToDealFrom = gameState.getDeckInGameState();
    List<Card> turnCards = gameState.getCardsOnTable();
    Card cardToDrawn = deckService.drawCardFromDeck(deckToDealFrom);
    turnCards.add(cardToDrawn);
    gameState.setCardsOnTable(turnCards);
  }
}