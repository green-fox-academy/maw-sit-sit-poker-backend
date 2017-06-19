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
  TokenService tokenService;

  public ResponseType responseToSuccessfulRegister(PokerUser userRegister){
    pokerUserRepo.save(userRegister);
    String token = tokenService.generateToken(userRegister);
    return new UserTokenResponse("success", token, userRegister.getId());
  }

  public ResponseType responseToSuccessfulLogin(LoginRequest loginRequest) {
    PokerUser pokerUserFromDatabase = pokerUserRepo.findByUsername(loginRequest.getUsername());
    String passwordOfUsernameFromDatabase = pokerUserFromDatabase.getPassword();
    if (loginRequest.getPassword().equals(passwordOfUsernameFromDatabase)) {
      String token = tokenService.generateToken(pokerUserFromDatabase);
    return new UserTokenResponse("success", token, pokerUserFromDatabase.getId());
    }
    return null;
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

  public ResponseType loginWithIvalidUsernameOrPassword() {
    return new StatusError("fail", "invalid username or password");
  }

  public boolean isEmailOccupied(PokerUser pokerUser) {
    if (!pokerUserRepo.findByEmail(pokerUser.getEmail()).equals(null)) {
      return true;
    }
    return false;
  }

  public boolean isUsernameOccupied(PokerUser pokerUser) {
    if ((!pokerUserRepo.findByUsername(pokerUser.getUsername()).equals(null))){
      return true;
    }
    return false;
  }

  public boolean isUserExistsInDB(long id){
    if (pokerUserRepo.exists(id)){
      return true;
    } else
    return false;
  }

  public PokerUserDTO getUserDTO(long id){
      PokerUserDTO pokerUserDTO = new PokerUserDTO();
      PokerUser pokerUser = pokerUserRepo.findOne(id);
      pokerUserDTO.setId(id);
      pokerUserDTO.setUsername(pokerUser.getUsername());
      pokerUserDTO.setAvatar(pokerUser.getAvatar());
      pokerUserDTO.setChips(pokerUser.getChips());
      return pokerUserDTO;
  }

  public List<PokerUserDTO> getTopTenLeaderboard(){
    List<PokerUser> topTenList = pokerUserRepo.findTop10ByOrderByChipsDesc();
    List<PokerUserDTO> topTenDTO = new ArrayList<>();
    for (PokerUser user : topTenList) {
      topTenDTO.add(getUserDTO(user.getId()));
    }
    return topTenDTO;
  }
}
