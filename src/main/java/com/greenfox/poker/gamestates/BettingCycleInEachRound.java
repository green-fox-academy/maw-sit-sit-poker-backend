package com.greenfox.poker.gamestates;


import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.service.GameService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


public class BettingCycleInEachRound {

  private List<Long> playerIdNumbersAroundTheTable = new ArrayList<>();

  @Autowired
  GameState gameState;

  private GameService gameService;
  private List<GamePlayer> activeGamePlayersListAroundTheTableStartingFromFirstToAct;
  private int bettingRound;

  @Autowired
  public BettingCycleInEachRound(GameService gameService) {
    this.gameService = gameService;
  }

  public void initBettingCycle(long gameStateId) {
    gameState = gameService.getGameStates().get(gameStateId);
    bettingRound = 0;
    createActiveGamePlayersListAroundTheTableStartingFromFirstToAct(gameStateId);
  }

  public void bettingCycle(long gameStateId) {
    gameState = gameService.getGameStates().get(gameStateId);
    bettingRound = 0;
    createActiveGamePlayersListAroundTheTableStartingFromFirstToAct(gameStateId);
  }


  private void createActiveGamePlayersListAroundTheTableStartingFromFirstToAct(long gameStateId) {
    activeGamePlayersListAroundTheTableStartingFromFirstToAct = new ArrayList<>();
    gameState = gameService.getGameStates().get(gameStateId);
    List<GamePlayer> originalList = new ArrayList<>();
    int actorPlayerIndexInTheOriginalList = 0;
    for (GamePlayer gamePlayer : gameState.getPlayers()) {
      originalList.add(gamePlayer);
    }
    for (GamePlayer gameplayer : originalList) {
      if (gameplayer.getId() == gameState.getActorPlayerId()) {
        actorPlayerIndexInTheOriginalList = originalList.indexOf(gameplayer);
      }
    }
    for (int i = actorPlayerIndexInTheOriginalList; i < originalList.size(); i++) {
      if (!originalList.get(i).isFolded() && !originalList.get(i).isWaiting()) {
        activeGamePlayersListAroundTheTableStartingFromFirstToAct.add(originalList.get(i));
      }
    }
    for (int i = 0; i < actorPlayerIndexInTheOriginalList; i++) {
      if (!originalList.get(i).isFolded() && !originalList.get(i).isWaiting()) {
        activeGamePlayersListAroundTheTableStartingFromFirstToAct.add(originalList.get(i));
      }
    }
  }

  //todo
  private void markNextActivePlayerAsPlayerToAct(long gameStateId, long currentPlayerId) {
    long PlayerToActIdInTheNextRound = activeGamePlayersListAroundTheTableStartingFromFirstToAct
            .get(1).getId();
    gameState = gameService.getGameStates().get(gameStateId);
    GamePlayer playerToActInThisRound;
    GamePlayer playerToActInNextRound;
  }
}
