package com.greenfox.poker.service;

import com.greenfox.poker.model.ChipsToJoinGame;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.ResponseType;
import com.greenfox.poker.model.StatusError;
import com.greenfox.poker.repository.GameRepo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class GameService {

  @Autowired
  GameRepo gameRepo;

  @Autowired
  ErrorMessageService errorMessageService;

  //  List<GameState> gameStateList = new ArrayList<>();
  HashMap<Integer, GameState> gameStateMap;

  public Game saveGame(Game game) {
    gameRepo.save(game);
    return game;
  }

  public List<Game> getAllGamesOrderedByBigBlind() {
    return gameRepo.findAllByOrderByBigBlindDesc();
  }

  public boolean isGameExist(long id) {
    if (gameRepo.exists(id)) {
      return true;
    }
    return false;
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
    } else {
      return new ResponseEntity(saveGame(game), HttpStatus.OK);
    }
  }

  public ResponseEntity<?> joinTable(long id, ChipsToJoinGame chips, String token) {
    TokenService tokenService = new TokenService();
    String username = tokenService.getUsernameFromToken(token);
    Game game = getGameById(id);
    long gameStateId = game.getGamestateId();
    return new ResponseEntity(getGameState(gameStateId), HttpStatus.OK);
  }
}
