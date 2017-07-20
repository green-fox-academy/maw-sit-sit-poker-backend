package com.greenfox.poker.service;

import com.greenfox.poker.model.Card;
import com.greenfox.poker.model.Game;
import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.GameState;
import com.greenfox.poker.model.GamesList;
import com.greenfox.poker.model.PlayerAction;
import com.greenfox.poker.model.ResponseType;
import com.greenfox.poker.model.Round;
import com.greenfox.poker.model.StatusError;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.repository.GameRepo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {

  private final static Logger logger = Logger.getLogger(GameService.class.getName());

  ErrorMessageService errorMessageService;
  DtoService dtoService;
  GamePlayer gamePlayer;
  GameRepo gameRepo;

  @Autowired
  public GameService(ErrorMessageService errorMessageService,
          DtoService dtoService, GamePlayer gamePlayer, GameRepo gameRepo) {
    this.errorMessageService = errorMessageService;
    this.dtoService = dtoService;
    this.gamePlayer = gamePlayer;
    this.gameRepo = gameRepo;
  }

  private Game newGame = new Game();

  private HashMap<Long, GameState> gameStates = new HashMap<>();

  public HashMap<Long, GameState> getGameStates() {
    return gameStates;
  }

  public void setGameStateMap(
          HashMap<Long, GameState> gameStates) {
    this.gameStates = gameStates;
  }

  private void saveOneGameToRepo(String name, Integer blind, Integer maxPlayer) {
    newGame = new Game(name, blind, maxPlayer);
    gameRepo.save(newGame);
    logger.log(Level.INFO, "New GAME created and saved. game: " + newGame.toString());
    createGameState(newGame);
  }

  public void initDefaultGames() {
    if (!isThereAnyGameInRepo()) {
      saveOneGameToRepo("Green Table", 10, 4);
      saveOneGameToRepo("Red Table", 20, 4);
      saveOneGameToRepo("Blue Table", 50, 4);
    }
    else {
      recreateGameStatesforGamesInDB(gameRepo.findAll());
    }
  }

  public void recreateGameStatesforGamesInDB(List<Game> savedGames) {
    for (Game game : savedGames) {
      createGameState(game);
    }
  }

  public Game createNewGame(Game game) {
    newGame = new Game(game.getName(), game.getBigBlind(), game.getMaxPlayers());
    gameRepo.save(newGame);
    createGameState(newGame);
    logger.log(Level.INFO, "New GAME created and saved. game: " + newGame.toString());
    return newGame;
  }

  private void createGameState(Game game) {
    if (!hasGameAGameState(game.getId())) {
      long commonIdwithGame = gameRepo.findOneByName(game.getName()).getId();
      GameState newState = new GameState(commonIdwithGame);
      gameStates.put(commonIdwithGame, newState);
      for (int i = 0; i < game.getMaxPlayers(); i++) {
        newState.getPlayers().add(null);
      }
      logger.log(Level.INFO,
          "New GAMESTATE created: " + gameStates.get(commonIdwithGame).toString());
    }
  }

  private boolean hasGameAGameState(long gameId) {
    if (gameStates.containsKey(gameId)) {
      return true;
    }
    return false;
  }

  private Integer getNumberOfPlayersAtTable(GameState gameState) {
    int numberOfplayers = 0;
    for (GamePlayer player : gameState.getPlayers()) {
      if (player != null) {
        numberOfplayers++;
      }
    }
    return numberOfplayers;
  }

  private boolean isThereAtLeastTwoPlayersToPlay(GameState gameState) {
    if (getNumberOfPlayersAtTable(gameState) > 1) {
      return true;
    } else {
      return false;
    }
  }

  public void deleteGame(Game game) {
    gameRepo.delete(game);
  }

  public GamesList getAllGamesOrderedByBigBlind() {
    GamesList gamesList = new GamesList();
    gamesList.setGames(gameRepo.findAllByOrderByBigBlindDesc());
    logger.log(Level.INFO, "All games ordered by big blind: " + gamesList.toString());
    return gamesList;
  }

  public Game getGameById(long gameId) {
    return gameRepo.findOne(gameId);
  }

  public GameState getGameStateById(long id) {
    return gameStates.get(id);
  }

  public boolean isPlayerInTheGame(long playerId, long gameId) {
    if (getPlayersListFromGame(gameId).isEmpty()) {
      logger.log(Level.WARNING,
              "Checking if player is in the game / nobody is in this game: " + getGameById(gameId));
      return false;
    }
    for (GamePlayer player : getPlayersListFromGame(gameId)) {
      if (player != null && player.getId() == playerId) {
        logger.log(Level.INFO,
                "Checking if player is in the game / player is in this game: " + getGameById(
                        gameId));
        return true;
      }
    }
    return false;
  }

  private boolean isThereEmptySeatAtTheGame(long gameId) {
    if (getGameStateById(gameId).getPlayers().contains(null)) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean isGameExistById(long id) {
    return gameRepo.exists(id);
  }

  public boolean isGameExistByName(String name) {
    if (gameRepo.findAllByName(name).size() > 0) {
      return true;
    }
    return false;
  }

  private boolean isThereAnyGameInRepo () {
    if (gameRepo.findAll().size() == 0) {
      return false;
    }
    else {
      return true;
    }
  }

  private List<Integer> getEmptySeatIndexesAtGame(long gameId) {
    List<Integer> nulls = new ArrayList<>();
    List<GamePlayer> playerList = getGameStateById(gameId).getPlayers();
    for (int i = 0; i < playerList.size(); i++) {
      if (playerList.get(i) == null) {
        nulls.add(i);
      }
    }
    return nulls;
  }

  public GamePlayer createNewPlayer(PokerUserDTO user, long chipsToPlayWith) {
    gamePlayer = new GamePlayer(chipsToPlayWith, user);
    return gamePlayer;
  }

  public void addPlayerToGame(long gameId, GamePlayer gamePlayer) {
      int numberOfEmptySeats = getEmptySeatIndexesAtGame(gameId).size();
      int randomSeatIndex = (int) (Math.random()*numberOfEmptySeats);
      Integer emptySeat = getEmptySeatIndexesAtGame(gameId).get(randomSeatIndex);
      gameStates.get(gameId).getPlayers().add(emptySeat, gamePlayer);
      logger.log(Level.INFO,
        "New gameplayer created and added to the game: gameplayer username: " + gamePlayer
            .getUsername() + ", game id:" + gameStates.get(gameId));
  }

  public void setRoundToBetting(GameState gameState) {
    if (isThereAtLeastTwoPlayersToPlay(gameState)) {
      gameStates.get(gameState.getId()).setRound(Round.BETTING);
    }
  }

  public void joinPlayerToGame(PokerUserDTO user, long gameId, long chipsToPlayWith) {
    addPlayerToGame(gameId, createNewPlayer(user, chipsToPlayWith));
    logger.log(Level.INFO,
        "join pokerUserDTO to game");
    setRoundToBetting(gameStates.get(gameId));
  }

  public ResponseType respondToJoinTable(PokerUserDTO user, long gameId) {
    String gameName = gameRepo.findOne(gameId).getName();
    String playerName = user.getUsername();
    return new StatusError("success", playerName + " joined game: " + gameName);
  }

  private int getTableBigBlind(long gameId) {
    return gameRepo.findOne(gameId).getBigBlind();
  }

  public void removePlayerFromGame(long playerId, long gameId) {
    logger.log(Level.INFO,
            "remove player (playerId " + playerId + " from the game: " + gameId);
    getPlayersListFromGame(gameId).set(getPlayerIndexFromGameStateByIds(playerId, gameId), null);
  }

  public ResponseType respondToLeave(long playerId, long gameId) {
    String playerName = dtoService.pokerUserDTOs.get(playerId).getUsername();
    String gameName = gameRepo.findOne(gameId).getName();
    return new StatusError("success", playerName + " left game: " + gameName);
  }

  private Integer getPlayerIndexFromGameStateByIds(long playerId, long gameId) {
    int indexOfplayer = -1;
    List<GamePlayer> playersList = getPlayersListFromGame(gameId);
      for (GamePlayer player : playersList) {
        if (player != null) {
          if (player.getId() == playerId) {
            indexOfplayer = playersList.indexOf(player);
          }
        }
      }
    return indexOfplayer;
  }

  private Integer getPlayerIndexFromGameStateByObjects(GamePlayer player, Game game) {
    return getPlayersListFromGame(game.getId()).indexOf(player);
  }

  public void updateGame(long playerId, long gameId, PlayerAction playerAction) {
    gamePlayer = getPlayersListFromGame(gameId).get(getPlayerIndexFromGameStateByIds(playerId, gameId));
    setActingPlayerIdInGameState(playerId, gameId);
    setLastActionAndBetOfPlayer(gamePlayer, gameId, playerAction);
    getPlayersListFromGame(gameId).set(getPlayerIndexFromGameStateByIds(playerId, gameId), gamePlayer);
    gameStates.get(gameId).setPlayers(getPlayersListFromGame(gameId));
    logger.log(Level.INFO,
            "update game");
  }

  private List<GamePlayer> getPlayersListFromGame(long gameId) {
    return getGameStateById(gameId).getPlayers();
  }

  private void setActingPlayerIdInGameState(long playerId, long gameId) {
    gameStates.get(gameId).setActorPlayerId(playerId);
    logger.log(Level.INFO,
            "set acting player id in game state for the following player id:" + playerId);
  }

  private void setLastActionAndBetOfPlayer(PokerUserDTO pokerUserDTO, long gameId, PlayerAction playerAction) {
    gamePlayer = getPlayersListFromGame(gameId).get(getPlayerIndexFromGameStateByIds(pokerUserDTO.getId(), gameId));
    gamePlayer.setLastAction(playerAction.getAction());
    gamePlayer.setBet(gamePlayer.getBet() + (int) playerAction.getValue());
    logger.log(Level.INFO,
            "set last actions and bet");
  }

  private List<Card> getPlayersCards(PokerUserDTO pokerUserDTO, long gameId) {
    int playerIndex = getPlayerIndexFromGameStateByIds(pokerUserDTO.getId(), gameId);
    return getPlayersListFromGame(gameId).get(playerIndex).getPlayersHand();
  }

  public List<String> getPlayersHandFromTable(PokerUserDTO pokerUserDTO, long gameId) {
    List<String> cardsAsString = new ArrayList<>();
    for (Card c : getPlayersCards(pokerUserDTO, gameId)) {
     cardsAsString.add(c.toString());
    }
    return cardsAsString;
  }
}
