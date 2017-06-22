package com.greenfox.poker.service;

import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.repository.GameRepo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {

  @Autowired
  GameRepo gameRepo;

  @Autowired
  UserService userService;

  HashMap<Long, GameState> gameStateMap = new HashMap<>();

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
    return gameStateMap.get(id);
  }

  public List<PokerUserDTO> getUserDTOListFromGame(Game game){
    long thisGameStateId = game.getGamestate_id();
    List<PokerUserDTO> currentPlayersInTheGame = new ArrayList<>();
    List<GamePlayer> gamePlayers = new ArrayList<>();
    gamePlayers = gameStateMap.get(thisGameStateId).getPlayers();
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

  public void joinPlayerToGame(PokerUserDTO pokerUserDTO, long gameId){
    GamePlayer newPlayer = new GamePlayer(pokerUserDTO);
    long stateId = gameRepo.findOne(gameId).getGamestate_id();
    getGameState(stateId).getPlayers().add(newPlayer);
  }
}