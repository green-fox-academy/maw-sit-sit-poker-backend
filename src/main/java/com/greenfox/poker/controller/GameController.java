package com.greenfox.poker.controller;


import com.greenfox.poker.model.ChipsToJoinGame;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GameState;
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

  @RequestMapping(value = "/games", method = RequestMethod.GET)
  public List<Game> getGamesList() {
    return gameService.getAllGamesOrderedByBigBlind();
  }

  @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> gameState(@PathVariable("id") long id) {
    if (gameService.isGameExist(id)) {
      long currentStateId = gameService.getGamebyId(id).getGamestate_id();
      return new ResponseEntity(gameService.getGameState(currentStateId), HttpStatus.OK);
    }
    return new ResponseEntity(new StatusError("fail", "game id doesn’t exist"), HttpStatus.NOT_FOUND);
  }

  @RequestMapping(value = "/savenewgames", method = RequestMethod.POST)
  public ResponseEntity<?> saveNewGame(@RequestBody @Valid Game game, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ResponseEntity(userService.respondToMissingParameters(bindingResult),
          HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity(gameService.saveGame(game), HttpStatus.OK);
    }
  }
}



