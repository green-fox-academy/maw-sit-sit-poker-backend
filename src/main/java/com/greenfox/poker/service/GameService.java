package com.greenfox.poker.service;

import com.greenfox.poker.model.ChipsToJoinGame;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.StatusError;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.repository.GameRepo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class GameService {

  GameRepo gameRepo;

  @Autowired
  ErrorMessageService errorMessageService;

  @Autowired
  UserService userService;

  @Autowired
  public GameService(GameRepo gameRepo) {
    this.gameRepo = gameRepo;
  }

  HashMap<Long, GameState> gameStateMap = new HashMap<>();

  public HashMap<Long, GameState> getGameStateMap() {
    return gameStateMap;
  }

  public void setGameStateMap(
          HashMap<Long, GameState> gameStateMap) {
    this.gameStateMap = gameStateMap;
  }

  public Game saveGame(Game game) {
    gameRepo.save(game);
    return game;
  }

  public void deleteGame(Game game) {
    gameRepo.delete(game);
  }

  public List<Game> getAllGamesOrderedByBigBlind() {
    return gameRepo.findAllByOrderByBigBlindDesc();
  }

  public boolean isGameExist(long id) {
    return (gameRepo.exists(id));
  }

  public Game getGameById(long id) {
    return gameRepo.findOne(id);
  }

  public GameState getGameState(long id) {
    return gameStateMap.get(id);
  }

  public ResponseEntity<?> getGameStateById(long id) {
    if (isGameExist(id)) {
      long currentStateId = getGameById(id).getGamestateId();
      return new ResponseEntity(getGameState(currentStateId), HttpStatus.OK);
    }
    return new ResponseEntity(new StatusError("fail", "game id doesn’t exist"),
            HttpStatus.NOT_FOUND);
  }

  public ResponseEntity<?> saveNewGame(Game game, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ResponseEntity(errorMessageService.respondToMissingParameters(bindingResult),
              HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity(saveGame(game), HttpStatus.OK);
  }

  public List<PokerUserDTO> getUserDTOListFromGame(Game game) {
    long thisGameStateId = game.getGamestateId();
    List<PokerUserDTO> currentPlayersInTheGame = new ArrayList<>();
    List<GamePlayer> gamePlayers = gameStateMap.get(thisGameStateId).getPlayers();
    for (GamePlayer player : gamePlayers) {
      currentPlayersInTheGame.add(player.getPlayer());
    }
    return currentPlayersInTheGame;
  }


  public boolean isPlayerAlreadyInTheGame(long gameId, long userId) {
    for (PokerUserDTO userDto : getUserDTOListFromGame(gameRepo.findOne(gameId))) {
      if (userDto.getId() == userId) {
        return true;
      }
    }
    return false;
  }

  public void joinPlayerToGame(PokerUserDTO pokerUserDTO, long gameId) {
    GamePlayer newPlayer = new GamePlayer(pokerUserDTO);
    long stateId = gameRepo.findOne(gameId).getGamestateId();
    getGameState(stateId).getPlayers().add(newPlayer);
  }
}
