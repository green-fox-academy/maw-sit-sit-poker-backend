package com.greenfox.poker.service;


import com.greenfox.poker.model.ResponseType;
import com.greenfox.poker.model.StatusError;
import com.greenfox.poker.repository.GameRepo;
import com.greenfox.poker.repository.PokerUserRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class ErrorMessageService {

  private final static Logger logger = Logger.getLogger(DtoService.class.getName());

  @Autowired
  PokerUserRepo pokerUserRepo;

  @Autowired
  GameRepo gameRepo;

  public ResponseType respondToMissingParameters(BindingResult bindingResult) {
    logger.log(Level.WARNING, "respond to missing parameters");
    List<String> missing = new ArrayList<>();
    String missingFields = new String();
    for (FieldError fielderror : bindingResult.getFieldErrors()) {
      missing.add(fielderror.getField());
    }
    missingFields =
            "Missing parameter(s): " + missing.stream().collect(Collectors.joining(", ")) + "!";
    return new StatusError("fail", missingFields);
  }

  public ResponseType responseToWrongGameId() {
    logger.log(Level.WARNING, "respond to wrong game id");
    return new StatusError("fail", "game id doesn't exist");
  }

  public ResponseType joinToGameWhereUserPlaysAlready(long playerId, long gameId) {
    logger.log(Level.WARNING, "respond to join request when player joined already");
    String playerName = pokerUserRepo.findOne(playerId).getUsername();
    String gameName = gameRepo.findOne(gameId).getName();
    return new StatusError("fail", playerName + " already joined game: " + gameName);
  }

  public ResponseType respondToPlayerNotSitingAtTheGame(long playerId, long gameId) {
    logger.log(Level.WARNING, "respond to player not sitting at the game");
    String playerName = pokerUserRepo.findOne(playerId).getUsername();
    String gameName = gameRepo.findOne(gameId).getName();
    return new StatusError("fail", playerName + " was not a player in game: " + gameName);
  }

  public ResponseType joinGameWithNotEnoughChips() {
    logger.log(Level.WARNING, "respond to join request with not enough chips");
    return new StatusError("fail", "you dont have enough chips to play with");
  }

  public ResponseType responseToAlreadyExistingGameNameInRepo() {
    logger.log(Level.WARNING, "respond to already existing game");
    return new StatusError("fail", "This game name already exists");
  }
}
