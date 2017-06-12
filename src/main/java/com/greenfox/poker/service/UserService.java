package com.greenfox.poker.service;

import com.greenfox.poker.model.PokerUser;
import org.springframework.stereotype.Component;

@Component
public class UserService {

  public UserService() {
  }

  public PokerUser registerNewUser(PokerUser newUserToRegister){
    PokerUser pokerUser = new PokerUser(newUserToRegister.getUsername(), newUserToRegister.getPassword(),newUserToRegister.getEmail(),newUserToRegister.getAvatar());
    return pokerUser;
  }
}
