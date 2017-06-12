package com.greenfox.poker.service;

import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.RegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class UserService {

  public UserService() {
  }

  public RegisterResponse registerNewUser(PokerUser newUserToRegister){
    return new RegisterResponse(newUserToRegister.getId(), newUserToRegister.getUsername(), newUserToRegister.getEmail(), newUserToRegister.getCash(), newUserToRegister.getAvatar());
  }

  public RegisterResponse respondToRegisterError(PokerUser newUserToRegister){
    return new RegisterResponse("message");
  }
}
