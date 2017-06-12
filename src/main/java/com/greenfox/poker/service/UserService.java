package com.greenfox.poker.service;

import com.greenfox.poker.model.ResponseDataObject;
import com.greenfox.poker.model.User;
import com.greenfox.poker.model.UserRegister;
import org.springframework.stereotype.Component;

@Component
public class UserService {

  public UserService() {
  }

  public ResponseDataObject registerNewUser(UserRegister newUserRegister){
    User newUser = new User(newUserRegister.getUsername(), newUserRegister.getPassword(),newUserRegister.getEmail(),newUserRegister.getAvatar());
    return new ResponseDataObject(newUser);
  }
}
