package com.greenfox.poker.gamestates;


import com.greenfox.poker.model.Action;
import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GamePlayerHand;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.Round;
import com.greenfox.poker.service.DeckService;
import com.greenfox.poker.service.GameService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class Betting {

  private GameService gameService;
  private GameState gameState;
  private DeckService deckService;
  private List<Long> playerIdNumbersAroundTheTable = new ArrayList<>();
  private long smallBlindPlayerId;
  private long bigBlindPlayerId;
  private GamePlayerHand gamePlayerHand;

  @Autowired
  public Betting(GameService gameService) {
    this.gameService = gameService;
  }

  public void initForBettingGameState(long gameStateId) {
    removeGamePlayersFromTableWithLessChipsThankBigBlind(gameStateId);
    checkIfThereAreAtLeastTwoPlayersToPlay(gameStateId);
    if (gameService.getGameStateMap().get(gameStateId).getRound() == Round.BETTING) {
      setAllPlayerAtTheTableToActive(gameStateId);
      setGameStateSettingsToDefault(gameStateId);
      assignDealer(gameStateId);
      setAndAutobetSmallBlindBigBlind(gameStateId);
      getNewDeckAndSetItInGameState(gameStateId);
      shuffleDeck(gameStateId);
      initGamePlayerHandForEachGamePlayer(gameStateId);
      drawTwoCardsToEachPlayer(gameStateId);
    }
  }

  private void removeGamePlayersFromTableWithLessChipsThankBigBlind(long gameStateId) {
    gameState = gameService.getGameStateMap().get(gameStateId);
    Integer tableBigBlind = gameService.getTableBigBlind(gameStateId);
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      if (gamePlayer.getChips() < tableBigBlind) {
        gameService.getGameStateMap().remove(gamePlayer);
      }
    }
  }

  private void checkIfThereAreAtLeastTwoPlayersToPlay(long gameStateId) {
    gameState = gameService.getGameStateMap().get(gameStateId);
    if (gameState.getPlayers().size() < 2) {
      gameState.setRound(Round.IDLE);
    }
  }

  private void setAllPlayerAtTheTableToActive(long gameStateId) {
    gameState = gameService.getGameStateMap().get(gameStateId);
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      gamePlayer.setWaiting(true);
      gamePlayer.setFolded(false);
      gamePlayer.setLastAction(Action.NONE);
    }
  }

  private void assignDealer(long gameStateId) {
    gameState = gameService.getGameStateMap().get(gameStateId);
    if (gameState.getDealerPlayerId() != null) {
      gameState.setDealerPlayerId(
              findNextPlayerIdAtTheTable(gameStateId, gameState.getDealerPlayerId()));
    } else {
      gameState.setDealerPlayerId(gameState.getPlayers().get(0).getId());
    }
  }

  private void setGameStateSettingsToDefault(long gameStateId) {
    gameState = gameService.getGameStateMap().get(gameStateId);
    gameState.setPot(0);
    gameState.setCardsOnTable(null);
    gameState.setRound(Round.BETTING);
  }

  private Long findNextPlayerIdAtTheTable(long gameStateId, Long currentPlayerId) {
    gameState = gameService.getGameStateMap().get(gameStateId);
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      if (gamePlayer.getId() != null) {
        playerIdNumbersAroundTheTable.add(gamePlayer.getId());
      }
    }
    int playersListSize = playerIdNumbersAroundTheTable.size();
    int indexOfCurrentPlayerId = playerIdNumbersAroundTheTable.indexOf(currentPlayerId);
    if (indexOfCurrentPlayerId + 1 == playersListSize) {
      return playerIdNumbersAroundTheTable.get(0);
    }
    return playerIdNumbersAroundTheTable.get(indexOfCurrentPlayerId + 1);
  }

  private void setAndAutobetSmallBlindBigBlind(long gameStateId) {
    gameState = gameService.getGameStateMap().get(gameStateId);
    int bigBlindAmount = gameService.getTableBigBlind(gameStateId);
    int smallBlindAmount = bigBlindAmount / 2;
    smallBlindPlayerId = findNextPlayerIdAtTheTable(gameStateId,
            gameState.getDealerPlayerId());
    bigBlindPlayerId = findNextPlayerIdAtTheTable(gameStateId, smallBlindPlayerId);
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      if (gamePlayer.getId() == smallBlindPlayerId) {
        gamePlayer.setChips(gamePlayer.getChips() - smallBlindAmount);
        gamePlayer.setBet(gamePlayer.getBet() + smallBlindAmount);
        gameState.setPot(gameState.getPot() + smallBlindAmount);
      }
      if (gamePlayer.getId() == bigBlindPlayerId) {
        gamePlayer.setChips(gamePlayer.getChips() - bigBlindAmount);
        gamePlayer.setBet(gamePlayer.getBet() + bigBlindAmount);
        gameState.setPot(gameState.getPot() + bigBlindAmount);
      }
    }
    gameState.setActorPlayerId(findNextPlayerIdAtTheTable(gameStateId, bigBlindPlayerId));
  }

  private void getNewDeckAndSetItInGameState(long gameStateId) {
    gameState = gameService.getGameStateMap().get(gameStateId);
    Deck deck = deckService.getNewDeck();
    gameState.setDeckInGameState(deck);
  }

  private void shuffleDeck(long gameStateId) {
    gameState = gameService.getGameStateMap().get(gameStateId);
    Deck deckToShuffle = gameState.getDeckInGameState();
    deckService.shuffleDeck(deckToShuffle);
  }

  private void initGamePlayerHandForEachGamePlayer(long gameStateId) {
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      gamePlayerHand = new GamePlayerHand();
      gamePlayerHand.setGameStateId(gameStateId);
      gamePlayerHand.setGamePlayerHandId(gamePlayer.getId());
      gameState.addGamePlayerHandToGamePlayerHandList(gamePlayerHand);
    }
  }

  private void drawTwoCardsToEachPlayer(long gameStateId) {
    gameState = gameService.getGameStateMap().get(gameStateId);
    int totalCardsToDeal = playerIdNumbersAroundTheTable.size() * 2;
    Deck deckToDealFrom = gameState.getDeckInGameState();
    long dealCardToThisPlayerId = smallBlindPlayerId;
    for (int i = 0; i < totalCardsToDeal; i++) {
      Card drawnCard = deckService.drawCardFromDeck(deckToDealFrom);
      for (GamePlayerHand gamePlayerHand : gameState.getGamePlayerHandList()) {
        if (gamePlayerHand.getGameStateId() == gameStateId
                && gamePlayerHand.getGamePlayerHandId() == dealCardToThisPlayerId) {
          gamePlayerHand.addCardToGamePlayerOwnTwoCards(drawnCard);
        }
      }
      dealCardToThisPlayerId = findNextPlayerIdAtTheTable(gameStateId, dealCardToThisPlayerId);
    }
  }
}
