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

  GameService gameService;

  @Autowired
  public Flop(GameService gameService) {
    this.gameService = gameService;
  }

  public void initForBettingGameState(long gameStateId){
    getFlopThreeCards(gameStateId);
  }

  private void getFlopThreeCards(long gameStateId) {
    DeckService deckService = new DeckService();
    GameState gameState = gameService.getGameStates().get(gameStateId);
    Deck deckToDealFrom = gameState.getDeckInGameState();
    List<Card> flopCards = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      Card cardToDrawn = deckService.drawCardFromDeck(deckToDealFrom);
      flopCards.add(cardToDrawn);
    }
    gameState.setCardsOnTable(flopCards);
  }
}
