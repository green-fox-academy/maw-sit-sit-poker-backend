package com.greenfox.poker.service;

import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.model.ResponseType;
import com.greenfox.poker.repository.GameRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {

  @Autowired
  GameRepo gameRepo;

  @Autowired
  UserService userService;

  List<GameState> gameStateList = new ArrayList<>();

  public Game saveGame(Game game){
    gameRepo.save(game);
    return game;
  }

  public List<Game> getAllGamesOrderedByBigBlind(){
    return gameRepo.findAllByOrderByBigBlindDesc();
  }

  public boolean isGameExist(long id){
    if (gameRepo.exists(id)) {
      return true;
    }
    return false;
  }

  public Game getGamebyId(long id){
    return gameRepo.findOne(id);
  }

  public GameState getGameState(long id) {
    for (GameState gameState : gameStateList) {
      if (id == gameState.getId()) {
        return gameState;
      }
    }
    return new GameState();
  }

  public List<PokerUserDTO> getUserDTOListFromGame(Game game){
    long thisGameStateId = game.getGamestate_id();
    List<PokerUserDTO> currentPlayersInTheGame = new ArrayList<>();
    List<GamePlayer> gamePlayers = new ArrayList<>();
    for (GameState state : gameStateList) {
      if (state.getId() == thisGameStateId){
       gamePlayers = state.getPlayers();
      }
    }
    for (GamePlayer player : gamePlayers){
      currentPlayersInTheGame.add(player.getPlayer());
    }
    return currentPlayersInTheGame;
  }

  public boolean isPlayerAlreadyInTheGame(long gameId, long userId){
    for (PokerUserDTO userDto : getUserDTOListFromGame(gameRepo.findOne(gameId))){
      if (userDto.getId() == userId){
        return true;
      }
    }
    return false;
  }

  public void updateGameStateList(GameState gameState, int indexOfGameStateInList){
    gameStateList.set(indexOfGameStateInList, gameState);
  }

  public void joinPlayerToGame(PokerUserDTO pokerUserDTO, long gameId){
    GamePlayer newPlayer = new GamePlayer(pokerUserDTO);
    GameState currentGameState = getGameState(gameRepo.findOne(gameId).getGamestate_id());
    int index = gameStateList.indexOf(currentGameState);
    currentGameState.getPlayers().add(newPlayer);
    updateGameStateList(currentGameState, index);
  }
}