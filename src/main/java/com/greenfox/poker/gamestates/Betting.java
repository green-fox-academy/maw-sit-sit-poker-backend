package com.greenfox.poker.gamestates;


import com.greenfox.poker.model.Action;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.Round;
import com.greenfox.poker.service.GameService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class Betting {

  GameService gameService;

  @Autowired
  public Betting(GameService gameService) {
    this.gameService = gameService;
  }

  public void gameStateBettingInit(long gameStateId) {
    removeGamePlayersFromTableWithLessChipsThankBigBlind(gameStateId);
    checkIfThereAreAtLeastTwoPlayersToPlay(gameStateId);
    if (gameService.getGameStateMap().get(gameStateId).getRound() == Round.BETTING) {
      setAllPlayerAtTheTableToActive(gameStateId);
      assignDealer(gameStateId);
      autoBetSmallBlindBigBlind(gameStateId);
      drawPlayersCards(gameStateId);
    }
  }

  private void removeGamePlayersFromTableWithLessChipsThankBigBlind(long gameStateId) {
    GameState gameState = gameService.getGameStateMap().get(gameStateId);
    Integer tableBigBlind = gameService.getTableBigBlind(gameStateId);
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      if (gamePlayer.getChips() < tableBigBlind) {
        gameService.getGameStateMap().remove(gamePlayer);
      }
    }
  }

  private void checkIfThereAreAtLeastTwoPlayersToPlay(long gameStateId) {
    GameState gameState = gameService.getGameStateMap().get(gameStateId);
    if (gameState.getPlayers().size() < 2) {
      gameState.setRound(Round.WAITING);
    }
  }

  private void setAllPlayerAtTheTableToActive(long gameStateId) {
    GameState gameState = gameService.getGameStateMap().get(gameStateId);
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      gamePlayer.setActive(true);
      gamePlayer.setFolded(false);
      gamePlayer.setLastAction(Action.NONE);
    }
  }

  private void assignDealer(long gameStateId) {
    GameState gameState = gameService.getGameStateMap().get(gameStateId);
    if (gameState.getDealerPlayerId() != null) {
      gameState.setDealerPlayerId(
              findNextPlayerIdInAtTheTable(gameStateId, gameState.getDealerPlayerId()));
    } else {
      gameState.setDealerPlayerId(gameState.getPlayers().get(0).getId());
    }
  }

  private Long findNextPlayerIdInAtTheTable(long gameStateId, Long currentPlayerId) {
    GameState gameState = gameService.getGameStateMap().get(gameStateId);
    List<Long> playerIdNumbersAroundTheTable = new ArrayList<>();
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

  private void autoBetSmallBlindBigBlind(long gameStateId) {
  }

  private void drawPlayersCards(long gameStateId) {
  }

}
