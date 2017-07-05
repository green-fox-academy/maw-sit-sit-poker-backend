package com.greenfox.poker.controller;


import com.greenfox.poker.model.ChipsToJoinGame;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.PlayerAction;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.service.DtoService;
import com.greenfox.poker.service.ErrorMessageService;
import com.greenfox.poker.service.GameService;
import com.greenfox.poker.service.TokenService;
import com.greenfox.poker.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class GameController {

  @Autowired
  GameService gameService;

  @Autowired
  UserService userService;

  @Autowired
  TokenService tokenService;

  @Autowired
  ErrorMessageService errorMessageService;

  @Autowired
  DtoService dtoService;

  @Autowired
  PokerUserDTO pokerUserDTO;

  @RequestMapping(value = "/games", method = RequestMethod.GET)
  public ResponseEntity<?> getGamesList(@RequestHeader("X-poker-token") String token) {
    return new ResponseEntity(gameService.getAllGamesOrderedByBigBlind(), HttpStatus.OK);
  }


  @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
<<<<<<< HEAD
  public ResponseEntity<?> gameState(
      @PathVariable("id") long gameId,
      @RequestHeader("X-poker-token") String token) {
=======
  public ResponseEntity<?> gameState(@PathVariable("id") long gameId,
          @RequestHeader("X-poker-token") String token) {
>>>>>>> origin/NLGameLogicCycle
    if (gameService.isGameExistById(gameId)) {
      return new ResponseEntity(gameService.getGameById(gameId), HttpStatus.OK);
    }
    return new ResponseEntity(errorMessageService.responseToWrongGameId(), HttpStatus.NOT_FOUND);
  }

  @PutMapping("/game/{id}")
<<<<<<< HEAD
  public ResponseEntity<?> updateGameState(
      @PathVariable("id") long gameId,
      @RequestBody @Valid PlayerAction action, BindingResult bindingResult,
      @RequestHeader("X-poker-token") String token) {
     pokerUserDTO = tokenService.getPokerUserDTOFromToken(token);
=======
  public ResponseEntity<?> updateGameState(@PathVariable("id") long gameId,
          @RequestBody @Valid PlayerAction action, BindingResult bindingResult,
          @RequestHeader("X-poker-token") String token) {
    pokerUser = tokenService.getPokerUserFromToken(token);
>>>>>>> origin/NLGameLogicCycle
    if (bindingResult.hasErrors()) {
      return new ResponseEntity(errorMessageService.respondToMissingParameters(bindingResult),
              HttpStatus.BAD_REQUEST);
    }
    if (!gameService.isGameExistById(gameId)) {
      return new ResponseEntity(errorMessageService.responseToWrongGameId(), HttpStatus.NOT_FOUND);
    }
<<<<<<< HEAD
    if (!gameService.isPlayerInTheGame(pokerUserDTO.getId(), gameId)) {
      return new ResponseEntity(errorMessageService.respondToPlayerNotSitingAtTheGame(pokerUserDTO.getId(), gameId), HttpStatus.BAD_REQUEST);
=======
    if (!gameService.isPlayerInTheGame(gameId, pokerUser.getId())) {
      return new ResponseEntity(
              errorMessageService.respondToPlayerNotSitingAtTheGame(pokerUser.getId(), gameId),
              HttpStatus.BAD_REQUEST);
>>>>>>> origin/NLGameLogicCycle
    }
    gameService.updateGame(pokerUserDTO.getId(), gameId, action);

    return new ResponseEntity(HttpStatus.OK);
  }

  @RequestMapping(value = "/savenewgames", method = RequestMethod.POST)
<<<<<<< HEAD
  public ResponseEntity<?> saveNewGame(
      @RequestBody @Valid Game game,
      BindingResult bindingResult,
      @RequestHeader("X-poker-token") String token) {
=======
  public ResponseEntity<?> saveNewGame(@RequestBody @Valid Game game, BindingResult bindingResult,
          @RequestHeader String token) {
>>>>>>> origin/NLGameLogicCycle
    if (bindingResult.hasErrors()) {
      return new ResponseEntity(errorMessageService.respondToMissingParameters(bindingResult),
              HttpStatus.BAD_REQUEST);
    }
    if (gameService.isGameExistByName(game.getName())) {
      return new ResponseEntity(errorMessageService.responseToAlreadyExistingGameNameInRepo(),
              HttpStatus.CONFLICT);
    }
    return new ResponseEntity(gameService.createNewGame(game), HttpStatus.OK);
  }

  @PostMapping("/game/{id}/join")
<<<<<<< HEAD
  public ResponseEntity<?> joinTable (
      @PathVariable("id") long gameId,
      @RequestBody ChipsToJoinGame chips,
      @RequestHeader("X-poker-token") String token) {
    pokerUserDTO = tokenService.getPokerUserDTOFromToken(token);
    if (!gameService.isGameExistById(gameId)) {
      return new ResponseEntity(errorMessageService.responseToWrongGameId(), HttpStatus.NOT_FOUND);
    }
    if (gameService.isPlayerInTheGame(pokerUserDTO.getId(), gameId)) {
      return new ResponseEntity(errorMessageService.joinToGameWhereUserPlaysAlready(gameId, pokerUserDTO.getId()),
          HttpStatus.BAD_REQUEST);
    }
    if (!dtoService.hasPlayerEnoughChipsToPlay(chips.getChips(), pokerUserDTO.getId())) {
      return new ResponseEntity(errorMessageService.joinGameWithNotEnoughChips(), HttpStatus.BAD_REQUEST);
    }
    dtoService.deductChipsFromAvailableChips(chips.getChips(), pokerUserDTO.getId());
    return new ResponseEntity(gameService.joinPlayerToGame(pokerUserDTO, gameId, chips.getChips()), HttpStatus.OK);
=======
  public ResponseEntity<?> joinTable(@PathVariable("id") long gameId,
          @RequestBody ChipsToJoinGame chips, @RequestHeader("X-poker-token") String token) {
    pokerUser = tokenService.getPokerUserFromToken(token);
    if (!gameService.isGameExistById(gameId)) {
      return new ResponseEntity(errorMessageService.responseToWrongGameId(), HttpStatus.NOT_FOUND);
    }
    if (gameService.isPlayerInTheGame(gameId, pokerUser.getId())) {
      return new ResponseEntity(
              errorMessageService.joinToGameWhereUserPlaysAlready(gameId, pokerUser.getId()),
              HttpStatus.BAD_REQUEST);
    }
    if (!dtoService.hasPlayerEnoughChipsToPlay(chips.getChips(), pokerUser.getId())) {
      return new ResponseEntity(errorMessageService.joinGameWithNotEnoughChips(),
              HttpStatus.BAD_REQUEST);
>>>>>>> origin/NLGameLogicCycle
    }
    return new ResponseEntity(
            gameService.joinPlayerToGame(pokerUser.getId(), gameId, chips.getChips()),
            HttpStatus.OK);
  }

  @PostMapping("/game/{id}/leave")
<<<<<<< HEAD
  public ResponseEntity<?> leaveTable (
      @PathVariable("id") long gameId,
      @RequestHeader("X-poker-token") String token) {
    pokerUserDTO = tokenService.getPokerUserDTOFromToken(token);
    if (!gameService.isGameExistById(gameId)) {
      return new ResponseEntity(errorMessageService.responseToWrongGameId(), HttpStatus.NOT_FOUND);
    }
    if (!gameService.isPlayerInTheGame(pokerUserDTO.getId(), gameId)) {
      return new ResponseEntity(errorMessageService.respondToPlayerNotSitingAtTheGame(pokerUserDTO.getId(), gameId), HttpStatus.BAD_REQUEST);
    }
    gameService.removePlayerFromGame(pokerUserDTO.getId(), gameId);
    return new ResponseEntity(gameService.respondToLeave(pokerUserDTO.getId(),gameId), HttpStatus.OK);
=======
  public ResponseEntity<?> leaveTable(@PathVariable("id") long gameId,
          @RequestHeader("X-poker-token") String token) {
    pokerUser = tokenService.getPokerUserFromToken(token);
    if (!gameService.isGameExistById(gameId)) {
      return new ResponseEntity(errorMessageService.responseToWrongGameId(), HttpStatus.NOT_FOUND);
    }
    if (!gameService.isPlayerInTheGame(gameId, pokerUser.getId())) {
      return new ResponseEntity(
              errorMessageService.respondToPlayerNotSitingAtTheGame(pokerUser.getId(), gameId),
              HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity(gameService.leaveGame(gameId, pokerUser.getId()), HttpStatus.OK);
>>>>>>> origin/NLGameLogicCycle
  }
}



