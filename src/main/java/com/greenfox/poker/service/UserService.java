package com.greenfox.poker.service;

import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.RegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class UserService {

  public UserService() {
  }

  public boolean validateUserRegistration(PokerUser userRegister) {
    boolean validUserRegistration = true;
    if (!validateUsername(userRegister)) {
      validUserRegistration = false;
    }
    if (!validatePassword(userRegister)) {
      validUserRegistration = false;
    }
    if (!validateEmail(userRegister)) {
      validUserRegistration = false;
    }
    if (!validateAvatar(userRegister)) {
      validUserRegistration = false;
    }
    return validUserRegistration;
  }

  public boolean validateUserLogin(PokerUser userLogin) {
    boolean validateUserLogin = true;
    if (!validateUsername(userLogin)) {
      validateUserLogin = false;
    }
    if (!validatePassword(userLogin)) {
      validateUserLogin = false;
    }
    return validateUserLogin;
  }

  private boolean validateUsername(PokerUser userRegister) {
    return true;
  }

  private boolean validatePassword(PokerUser userRegister) {
    return true;
  }

  private boolean validateEmail(PokerUser userRegister) {
    return true;
  }

  private boolean validateAvatar(PokerUser userRegister) {
    return true;

  public RegisterResponse mockResponse() {
    return new RegisterResponse(1, "Bond", "james@bond.uk", null);
  }

  public RegisterResponse registerNewUser(PokerUser newUserToRegister) {
    return new RegisterResponse(newUserToRegister.getId(), newUserToRegister.getUsername(),
            newUserToRegister.getEmail(), newUserToRegister.getAvatar());
  }
  
  public RegisterResponse respondToRegisterError(String message) {
    return new RegisterResponse(message);
  }

  public RegisterResponse loginUser(PokerUser userLogin) {
    return mockResponse();
  }

  public RegisterResponse respondToLoginError(String message) {
    return new RegisterResponse(message);
  }
}
