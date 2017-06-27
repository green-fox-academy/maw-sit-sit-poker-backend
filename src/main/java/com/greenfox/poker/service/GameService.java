package com.greenfox.poker.service;

import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.ResponseType;
import com.greenfox.poker.model.StatusError;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.repository.GameRepo;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {

  GameRepo gameRepo;

  @Autowired
  ErrorMessageService errorMessageService;

  @Autowired
  UserService userService;

  @Autowired
  DtoService dtoService;

  @Autowired
  public GameService(GameRepo gameRepo) {
    this.gameRepo = gameRepo;
  }

  private Game newGame = new Game();

  private HashMap<Long, GameState> gameStateMap = new HashMap<>();

  public HashMap<Long, GameState> getGameStateMap() {
    return gameStateMap;
  }

  public void setGameStateMap(
      HashMap<Long, GameState> gameStateMap) {
    this.gameStateMap = gameStateMap;
  }

  public Game createNewGame(Game game) {
    newGame = new Game(game.getName(), game.getBigBlind(), game.getMaxPlayers());
    gameRepo.save(newGame);
    long commonId = gameRepo.findOneByName(game.getName()).getId();
    gameStateMap.put(commonId, new GameState(commonId));
    System.out.println(gameStateMap.toString());
    return newGame;
  }

  public void deleteGame(Game game) {
    gameRepo.delete(game);
  }

  public List<Game> getAllGamesOrderedByBigBlind() {
    return gameRepo.findAllByOrderByBigBlindDesc();
  }

  public boolean isGameExistById(long id) {
    return (gameRepo.exists(id));
  }

  public boolean isGameExistByName(String name) {
    if (gameRepo.findAllByName(name).size() > 0) {
      return true;
    }
    return false;
  }

  public Game getGameById(long gameId) {
    return gameRepo.findOne(gameId);
  }

  public GameState getGameStateById(long id) {
    return gameStateMap.get(id);
  }

  public boolean isPlayerAlreadyInTheGame(long gameId, long userId) {
    if (!gameStateMap.isEmpty()) {
      if (!gameStateMap.get(gameId).getPlayers().isEmpty()) {
        for (GamePlayer player : gameStateMap.get(gameId).getPlayers()) {
          if (player.getId() == userId) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public void createNewPlayerAndAddToGame(long playerId, long gameId, long chipsToPlayWith){
    PokerUserDTO player = dtoService.userDTOHashMap.get(playerId);
    GamePlayer newPlayer = new GamePlayer(chipsToPlayWith, player);
    gameStateMap.get(gameId).getPlayers().add(newPlayer);
  }

  public ResponseType joinPlayerToGame(long playerId, long gameId, long chipsToPlayWith){
    String gameName = gameRepo.findOne(gameId).getName();
    String playerName = dtoService.userDTOHashMap.get(playerId).getUsername();
    dtoService.deductChipsFromAvailableChips(chipsToPlayWith, playerId);
    createNewPlayerAndAddToGame(playerId,gameId,chipsToPlayWith);
    return new StatusError("success", playerName + " joined game: " + gameName);
  }
}
