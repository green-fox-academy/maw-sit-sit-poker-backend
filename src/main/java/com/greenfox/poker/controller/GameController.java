package com.greenfox.poker.controller;


import com.greenfox.poker.model.ChipsToJoinGame;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.StatusError;
import com.greenfox.poker.service.GameService;
import com.greenfox.poker.service.TokenService;
import com.greenfox.poker.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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

  TokenService tokenService;

  @RequestMapping(value = "/games", method = RequestMethod.GET)
  public List<Game> getGamesList() {
    return gameService.getAllGamesOrderedByBigBlind();
  }

  @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> gameState(@PathVariable("id") long id) {
    return gameService.getGameStateById(id);
  }

  @RequestMapping(value = "/savenewgames", method = RequestMethod.POST)
  public ResponseEntity<?> saveNewGame(@RequestBody @Valid Game game, BindingResult bindingResult) {
    return gameService.saveNewGame(game, bindingResult);
  }

  @PostMapping("/game/{id}/join")
  public ResponseEntity<?> joinTable(@PathVariable("id") long gameId, @RequestBody ChipsToJoinGame chips, @RequestHeader("X-poker-token") String token){
    PokerUser user = tokenService.getPokerUserFromToken(token);
    if (gameService.isGameExist(gameId)) {
      Game game = gameService.getGameById(gameId);
      long gameStateId = game.getGamestateId();
      if (gameService.isPlayerAlreadyInTheGame(gameId, user.getId())){
        return new ResponseEntity(new StatusError("fail", user.getUsername() + " has already joined the " + game.getName()), HttpStatus.BAD_REQUEST);
      }
      gameService.joinPlayerToGame(userService.getDTOWithChipsForGame(user.getId(), chips.getChips()), gameId);
      userService.deductChipsToSitDownWithFromUser(chips.getChips(), user.getId());
      return new ResponseEntity(gameService.getGameState(gameStateId), HttpStatus.OK);
    }
    return new ResponseEntity(new StatusError("fail", "game id doesn't exist"), HttpStatus.NOT_FOUND);
  }
}



