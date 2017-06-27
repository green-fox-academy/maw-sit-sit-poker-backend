package com.greenfox.poker.service;

import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.LoginRequest;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.model.ResponseType;
import com.greenfox.poker.model.UserTokenResponse;
import com.greenfox.poker.model.StatusError;
import com.greenfox.poker.repository.PokerUserRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

  String token;

  @Autowired
  PokerUserRepo pokerUserRepo;

  @Autowired
  PokerUser pokerUser;

  @Autowired
  TokenService tokenService;


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Autowired
  DtoService dtoService;

  @Autowired
  GameService gameService;


  public ResponseType responseToSuccessfulRegister(PokerUser pokerUser) {
    pokerUserRepo.save(pokerUser);
    this.token = tokenService.generateToken(pokerUser);
    return new UserTokenResponse("success", token, pokerUser.getId());
  }

  public ResponseType responseToSuccessfulLogin(LoginRequest loginRequest) {
    PokerUser pokerUserFromDatabase = pokerUserRepo.findByUsername(loginRequest.getUsername());
    this.token = tokenService.generateToken(pokerUserFromDatabase);
    dtoService.makePokerUserDTO(pokerUserFromDatabase.getId());
    return new UserTokenResponse("success", token, pokerUserFromDatabase.getId());
  }

  public ResponseType registerWithOccupiedEmail() {
    return new StatusError("fail", "email address already exists");
  }

  public ResponseType registerWithOccupiedUsername() {
    return new StatusError("fail", "username already exists");
  }

  public boolean isLoginValid(LoginRequest loginRequest) {
    if (pokerUserRepo.existsByUsername(loginRequest.getUsername()) &&
        pokerUserRepo.existsByPassword(loginRequest.getPassword())) {
      return true;
    }
    return false;
  }

  public ResponseType loginWithInvalidUsernameOrPassword() {
    return new StatusError("fail", "invalid username or password");
  }

  public boolean isEmailOccupied(PokerUser pokerUser) {
    boolean isEmailOccupied = false;
    isEmailOccupied = pokerUserRepo.existsByEmail(pokerUser.getEmail());
    return isEmailOccupied;
  }

  public boolean isUsernameOccupied(PokerUser pokerUser) {
    boolean isUsernameOccupied = false;
    isUsernameOccupied = pokerUserRepo.existsByUsername(pokerUser.getUsername());
    return isUsernameOccupied;
  }

  public long getUserIdFromUsername(String username) {
    return pokerUserRepo.findByUsername(username).getId();
  }

  public boolean isUserExistsInDB(long id) {
    if (pokerUserRepo.exists(id)) {
      return true;
    } else {
      return false;
    }
  }

  public List<PokerUserDTO> getTopTenLeaderboard() {
    List<PokerUser> topTenList = pokerUserRepo.findTop10ByOrderByChipsDesc();
    List<PokerUserDTO> topTenDTO = new ArrayList<>();
    for (PokerUser user : topTenList) {
      topTenDTO.add(dtoService.makePokerUserDTO(user.getId()));
    }
    return topTenDTO;
  }

  public void deductChipsToSitDownWithFromUser(long chipsToSitDownWith, long userId){
    pokerUser = pokerUserRepo.findOne(userId);
    long currentAmountOfChips = pokerUser.getChips();
    long amountOfChipsAfterDeduction = currentAmountOfChips - chipsToSitDownWith;
    pokerUser.setChips(amountOfChipsAfterDeduction);
    pokerUserRepo.save(pokerUser);
  }

  public PokerUserDTO getDTOWithChipsForGame(long pokerUserId, long chipsToSitDownWith){
    dtoService.pokerUserDTO = dtoService.makePokerUserDTO(pokerUserId);
    dtoService.pokerUserDTO.setChips(chipsToSitDownWith);
    return dtoService.pokerUserDTO;
  }

  public void updatePokerUserChipsInDBAfterEndOfGame(long chipsDifference, long playerId){
      pokerUserRepo.findOne(playerId).setChips(chipsDifference);
    }
  }
