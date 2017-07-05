package com.greenfox.poker.gamestates;


import com.greenfox.poker.model.Action;
import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Deck;
import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GamePlayerHand;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.Round;
import com.greenfox.poker.service.DeckService;


public class Betting {

  public Betting() {
  }

  public void initForBettingGameState(GameState gameState, int bigBlindAmount) {
    removeGamePlayersFromTableWithLessChipsThankBigBlind(gameState, bigBlindAmount);
    if (!isThereAtLeastTwoPlayersToPlay(gameState)) {
      setGameRoundToIdle(gameState);
    }
    if (gameState.getRound() == Round.BETTING) {
      setAllPlayerAtTheTableToActive(gameState);
      setGameStateSettingsToDefault(gameState);
      assignDealer(gameState);
      setSmallBlindAndBigBlindId(gameState);
      betSmallBlindBigBlind(gameState, bigBlindAmount);
      getNewDeckAndShuffleAndSetItInGameState(gameState);
      setPlayerHandsForAllPlayers(gameState);
    }
  }

  private void removeGamePlayersFromTableWithLessChipsThankBigBlind(GameState gameState,
          int bigBlindAmount) {
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      if (gamePlayer.getChips() < bigBlindAmount) {
        gameState.getPlayers().remove(gamePlayer);
      }
    }
  }

  private boolean isThereAtLeastTwoPlayersToPlay(GameState gameState) {
    if (gameState.getPlayers().size() < 2) {
      return false;
    }
    gameState.setRound(Round.BETTING);
    return true;
  }

  private void setGameRoundToIdle(GameState gameState) {
    gameState.setRound(Round.IDLE);
  }

  private void setAllPlayerAtTheTableToActive(GameState gameState) {
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      gamePlayer.setWaiting(true);
      gamePlayer.setFolded(false);
      gamePlayer.setLastAction(Action.NONE);
    }
  }

  private void assignDealer(GameState gameState) {
    if (gameState.getDealerPlayerId() != null) {
      gameState.setDealerPlayerId(findNextPlayerAtTheTable(gameState, gameState.getDealerPlayerId()));
    } else {
      gameState.setDealerPlayerId(gameState.getPlayers().get(0).getId());
    }
  }

  public void setSmallBlindAndBigBlindId(GameState gameState) {
    gameState.setSmallBlindPlayerId(
            findNextPlayerAtTheTable(gameState, gameState.getDealerPlayerId()));
    gameState.setBigBlindPlayerId(
            findNextPlayerAtTheTable(gameState, gameState.getSmallBlindPlayerId()));
  }

  private void setGameStateSettingsToDefault(GameState gameState) {
    gameState.setPot(0);
    gameState.setCardsOnTable(null);
    gameState.setRound(Round.BETTING);
  }

  private Long findNextPlayerAtTheTable(GameState gameState, Long currentPlayerId) {
    GamePlayer currentPlayer = gameState.getPlayers().stream()
            .filter(gamePlayer -> gamePlayer.getId() == currentPlayerId).findFirst().get();
    int currentPlayerIndex = gameState.getPlayers().indexOf(currentPlayer);
    Long nextPlayerId = null;
    for (int i = currentPlayerIndex + 1; i < gameState.getPlayers().size(); i++) {
      if (gameState.getPlayers().get(i) != null) {
        nextPlayerId = gameState.getPlayers().get(i).getId();
        return nextPlayerId;
      }
    }
    for (int i = 0; i < currentPlayerIndex; i++) {
      if (gameState.getPlayers().get(i) != null) {
        nextPlayerId = gameState.getPlayers().get(i).getId();
      }
    }
    return nextPlayerId;
  }

  private void betSmallBlindBigBlind(GameState gameState, int bigBlindAmount) {
    GamePlayer smallBlindPlayer = gameState.getPlayers().stream()
            .filter(gamePlayer -> gamePlayer.getId() == gameState.getSmallBlindPlayerId())
            .findFirst().get();
    smallBlindPlayer.setChips(smallBlindPlayer.getChips() - (bigBlindAmount / 2));
    smallBlindPlayer.setBet(smallBlindPlayer.getBet() + (bigBlindAmount / 2));
    gameState.setPot(gameState.getPot() + (bigBlindAmount / 2));
    GamePlayer bigBlindPlayer = gameState.getPlayers().stream()
            .filter(gamePlayer -> gamePlayer.getId() == gameState.getBigBlindPlayerId())
            .findFirst().get();
    bigBlindPlayer.setChips(bigBlindPlayer.getChips() - bigBlindAmount);
    bigBlindPlayer.setBet(bigBlindPlayer.getBet() + bigBlindAmount);
    gameState.setPot(gameState.getPot() + bigBlindAmount);
  }

  private void getNewDeckAndShuffleAndSetItInGameState(GameState gameState) {
    DeckService deckService = new DeckService();
    Deck deck = deckService.getNewDeck();
    deckService.shuffleDeck(deck);
    gameState.setDeckInGameState(deck);
  }

  private void setPlayerHandsForAllPlayers(GameState gameState) {
    DeckService deckService = new DeckService();
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      GamePlayerHand gamePlayerHand = new GamePlayerHand();
      gamePlayerHand.setGameStateId(gameState.getId());
      for (int i = 0; i < 2; i++) {
        Card drawnCard = deckService.drawCardFromDeck(gameState.getDeckInGameState());
        gamePlayerHand.addCardToGamePlayerHand(drawnCard);
      }
      gamePlayer.setGamePlayerHand(gamePlayerHand);
    }
  }
}
