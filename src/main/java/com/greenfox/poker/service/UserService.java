package com.greenfox.poker.service;

import com.greenfox.poker.model.LoginRequest;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.model.ResponseType;
import com.greenfox.poker.model.UserTokenResponse;
import com.greenfox.poker.model.StatusError;
import com.greenfox.poker.repository.PokerUserRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class UserService {

  @Autowired
  PokerUserRepo pokerUserRepo;

  @Autowired
  DtoService dtoService;

  @Autowired
  TokenService tokenService;

  public ResponseType responseToSuccessfulRegister(PokerUser pokerUser) {
    pokerUserRepo.save(pokerUser);
    String token = tokenService.generateToken(pokerUser);
    return new UserTokenResponse("success", token, pokerUser.getId());
  }

  public ResponseType responseToSuccessfulLogin(LoginRequest loginRequest) {
    PokerUser pokerUserFromDatabase = pokerUserRepo.findByUsername(loginRequest.getUsername()).get(0);
    String token = tokenService.generateToken(pokerUserFromDatabase);
    return new UserTokenResponse("success", token, pokerUserFromDatabase.getId());
  }

  public ResponseType respondToMissingParameters(BindingResult bindingResult) {
    List<String> missing = new ArrayList<>();
    String missingFields = new String();
    for (FieldError fielderror : bindingResult.getFieldErrors()) {
      missing.add(fielderror.getField());
    }
    System.out.println(missingFields);
    missingFields = "Missing parameter(s): " + missing.stream().collect(Collectors.joining(", ")) + "!";
    return new StatusError("fail", missingFields);
  }

  public ResponseType registerWithOccupiedEmail() {

    return new StatusError("fail", "email address already exists");
  }

  public ResponseType registerWithOccupiedUsername() {
    return new StatusError("fail", "username already exists");
  }

  public boolean isLoginValid(LoginRequest loginRequest){
    List<PokerUser> users = pokerUserRepo.findByUsername(loginRequest.getUsername());
    int sizeOfValidUsersListFromUserRepo = users.size();
    String passwordOfUserFromUserRepo = users.get(0).getPassword();
    if (sizeOfValidUsersListFromUserRepo > 0 && passwordOfUserFromUserRepo.equals(loginRequest.getPassword())){
      return true;
    }
    return false;
  }

  public ResponseType loginWithIvalidUsernameOrPassword() {
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

  public long getUserIdFromUsername(String username){
    return pokerUserRepo.findByUsername(username).get(0).getId();
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
}
