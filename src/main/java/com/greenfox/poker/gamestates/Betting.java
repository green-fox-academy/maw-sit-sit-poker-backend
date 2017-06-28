package com.greenfox.poker.gamestates;


import com.greenfox.poker.model.Action;
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
      assignDealerAndBlinds(gameStateId);
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
      gameState.setRound(Round.IDLE);
    }
  }

  private void setAllPlayerAtTheTableToActive(long gameStateId) {
    GameState gameState = gameService.getGameStateMap().get(gameStateId);
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      gamePlayer.setWaiting(true);
      gamePlayer.setFolded(false);
      gamePlayer.setLastAction(Action.NONE);
    }
  }

  private void assignDealerAndBlinds(long gameStateId) {
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
    GameState gameState = new GameState();
    int bigBlindAmount = gameService.getTableBigBlind(gameStateId);
    int smallBlindAmount = bigBlindAmount / 2;
    Long smallBlindPlayerId = findNextPlayerIdInAtTheTable(gameStateId,
            gameState.getDealerPlayerId());
    Long bigBlindPlayerId = findNextPlayerIdInAtTheTable(gameStateId, smallBlindPlayerId);
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
  }
  private void drawPlayersCards(long gameStateId) {


  }

}
