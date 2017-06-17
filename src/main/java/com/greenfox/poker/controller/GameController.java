package com.greenfox.poker.controller;


import com.greenfox.poker.model.Game;
import com.greenfox.poker.service.GameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

  @Autowired
  GameService gameService;

  @RequestMapping(value = "/games", method = RequestMethod.POST)
  public List<Game> getGamesList(){
    return gameService.getAllGamesOrderedByBigBlind();
  }
}
