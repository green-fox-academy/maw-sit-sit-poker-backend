package com.greenfox.poker.controller;


import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GamesList;
import com.greenfox.poker.service.GameService;
import com.greenfox.poker.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

  @Autowired
  GameService gameService;

  @Autowired
  UserService userService;

  @RequestMapping(value = "/games", method = RequestMethod.POST)
  public GamesList getGamesList() {
    return gameService.getAllGamesOrderedByBigBlind();
  }


  @RequestMapping(value = "/savenewgames", method = RequestMethod.POST)
  public ResponseEntity<?> saveNewGame(@RequestBody @Valid Game game, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
      return new ResponseEntity(userService.respondToMissingParameters(bindingResult),
              HttpStatus.BAD_REQUEST);
    }
    else return new ResponseEntity (gameService.saveGame(game), HttpStatus.OK);
  }
}



