package com.greenfox.poker.service;

import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.GamesList;
import com.greenfox.poker.model.PlayerAction;
import com.greenfox.poker.model.ResponseType;
import com.greenfox.poker.model.StatusError;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.repository.GameRepo;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {

  ErrorMessageService errorMessageService;
  DtoService dtoService;
  GamePlayer gamePlayer;
  GameRepo gameRepo;

  @Autowired
  public GameService(ErrorMessageService errorMessageService,
      DtoService dtoService, GamePlayer gamePlayer, GameRepo gameRepo) {
    this.errorMessageService = errorMessageService;
    this.dtoService = dtoService;
    this.gamePlayer = gamePlayer;
    this.gameRepo = gameRepo;
  }

  private Game newGame = new Game();

  private HashMap<Long, GameState> gameStates = new HashMap<>();

  public HashMap<Long, GameState> getGameStates() {
    return gameStates;
  }

  public void setGameStateMap(
      HashMap<Long, GameState> gameStates) {
    this.gameStates = gameStates;
  }

  public Game createNewGame(Game game) {
    newGame = new Game(game.getName(), game.getBigBlind(), game.getMaxPlayers());
    gameRepo.save(newGame);
    return newGame;
  }

  public void createGameState(Game game) {
    if (!hasGameAGameState(game.getId())) {
      long commonIdwithGame = gameRepo.findOneByName(game.getName()).getId();
      gameStates.put(commonIdwithGame, new GameState(commonIdwithGame));
    }
  }

  public boolean hasGameAGameState(long gameId) {
    if (gameStates.containsKey(gameId)) {
      return true;
    }
    return false;
  }

  public void deleteGame(Game game) {
    gameRepo.delete(game);
  }

  public GamesList getAllGamesOrderedByBigBlind() {
    GamesList gamesList = new GamesList();
    gamesList.setGames(gameRepo.findAllByOrderByBigBlindDesc());
    return gamesList;
  }

  public Game getGameById(long gameId) {
    return gameRepo.findOne(gameId);
  }

  public GameState getGameStateById(long id) {
    return gameStates.get(id);
  }

  public boolean isPlayerInTheGame(long playerId, long gameId) {
    if (!hasGameAGameState(gameId)) {
      return false;
    }
    if (getPlayersListFromGame(gameId).isEmpty()) {
      return false;
    }
    for (GamePlayer player : getPlayersListFromGame(gameId)){
      if (player != null && player.getId() == playerId){
        return true;
      }
    }
  return false;
  }

  public boolean isGameExistById(long id) {
    return gameRepo.exists(id);
  }

  public boolean isGameExistByName(String name) {
    if (gameRepo.findAllByName(name).size() > 0) {
      return true;
    }
    return false;
  }

  public void createNewPlayerAndAddToGame(PokerUserDTO user, long gameId, long chipsToPlayWith) {
    gamePlayer = new GamePlayer(chipsToPlayWith, user);
    gameStates.get(gameId).getPlayers().add(gamePlayer);
  }

  public ResponseType joinPlayerToGame(PokerUserDTO user, long gameId, long chipsToPlayWith) {
    createGameState(gameRepo.findOne(gameId));
    createNewPlayerAndAddToGame(user, gameId, chipsToPlayWith);
    return respondToJoinTable(user, gameId);
  }

  public ResponseType respondToJoinTable(PokerUserDTO user, long gameId) {
    String gameName = gameRepo.findOne(gameId).getName();
    String playerName = user.getUsername();
    return new StatusError("success", playerName + " joined game: " + gameName);
  }

  public int getTableBigBlind(long gameId) {
    return gameRepo.findOne(gameId).getBigBlind();
  }

  public void removePlayerFromGame(long playerId, long gameId) {
    getPlayersListFromGame(gameId).set(getPlayerIndexFromGameState(playerId, gameId), null);
  }

  public ResponseType respondToLeave(long playerId, long gameId) {
    String playerName = dtoService.pokerUserDTOs.get(playerId).getUsername();
    String gameName = gameRepo.findOne(gameId).getName();
    return new StatusError("success", playerName + " left game: " + gameName);
  }

  public Integer getPlayerIndexFromGameState(long playerId, long gameId) {
    gamePlayer = getPlayersListFromGame(gameId).stream()
        .filter(player -> player.getId().equals(playerId))
        .findFirst().get();
    return getPlayersListFromGame(gameId).indexOf(gamePlayer);
  }

  public void updateGame(long playerId, long gameId, PlayerAction playerAction) {
    gamePlayer = getPlayersListFromGame(gameId).get(getPlayerIndexFromGameState(playerId, gameId));
    setActingPlayerIdInGameState(playerId, gameId);
    setLastActionAndBetOfPlayer(gamePlayer, gameId, playerAction);
    getPlayersListFromGame(gameId).set(getPlayerIndexFromGameState(playerId, gameId), gamePlayer);
    gameStates.get(gameId).setPlayers(getPlayersListFromGame(gameId));
  }

  public List<GamePlayer> getPlayersListFromGame(long gameId) {
    return getGameStateById(gameId).getPlayers();
  }

  public void setActingPlayerIdInGameState(long playerId, long gameId) {
    gameStates.get(gameId).setActorPlayerId(playerId);
  }

  public void setLastActionAndBetOfPlayer(PokerUserDTO pokerUserDTO, long gameId, PlayerAction playerAction) {
    gamePlayer = getPlayersListFromGame(gameId).get(getPlayerIndexFromGameState(pokerUserDTO.getId(), gameId));
    gamePlayer.setLastAction(playerAction.getAction());
    gamePlayer.setBet(gamePlayer.getBet() + (int) playerAction.getValue());
  }
}
