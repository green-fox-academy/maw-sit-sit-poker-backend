package com.greenfox.poker.service;

import com.greenfox.poker.model.LoginRequest;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.UserTokenResponse;
import com.greenfox.poker.model.StatusError;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class UserService {

  public ResponseEntity<?> mockRespondToSuccessfulRegisterOrLogin() {
    return new ResponseEntity(new UserTokenResponse("success", "ABC123", 4321), HttpStatus.OK);
  }

  public ResponseEntity<?> respondToMissingParameters(BindingResult bindingResult) {
    List<String> listOfMissingFields = new ArrayList<>();
    String missingFields = new String();
    for (FieldError fielderror : bindingResult.getFieldErrors()) {
      listOfMissingFields.add(fielderror.getField());
    }
    missingFields = "Missing parameter(s): " + listOfMissingFields.toString();
    return new ResponseEntity(new StatusError("fail", missingFields), HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<?> respondToOccupiedConflict(String errorMessage) {
    StatusError statusError = new StatusError("fail", errorMessage);
    return new ResponseEntity(statusError, HttpStatus.CONFLICT);
  }

  public ResponseEntity<?> respondToInvalidUsernameOrPassword(String errorMessage) {
    StatusError statusError = new StatusError("fail", errorMessage);
    return new ResponseEntity(statusError, HttpStatus.UNAUTHORIZED);
  }

  public ResponseEntity<?> register(PokerUser pokerUser,
          BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return respondToMissingParameters(bindingResult);
    } else if (isEmailOccupied(pokerUser)) {
      return respondToOccupiedConflict("email address already exists");
    } else if (isUsernameOccupied(pokerUser)) {
      return respondToOccupiedConflict("username already exists");
    }
    return mockRespondToSuccessfulRegisterOrLogin();
  }

  public ResponseEntity<?> login(BindingResult bindingResult,
          LoginRequest loginRequest) {
    if (bindingResult.hasErrors()) {
      return respondToMissingParameters(bindingResult);
    } else if ((!loginRequest.getUsername().equals("Bond") || !loginRequest
            .getPassword().equals("password123"))) {
      return respondToInvalidUsernameOrPassword("invalid username or password");
    }
    return mockRespondToSuccessfulRegisterOrLogin();
  }

  public boolean isEmailOccupied(PokerUser pokerUser) {
    if (pokerUser.getEmail().equals("occupied@email.com")) {
      return true;
    }
    return false;
  }

  public boolean isUsernameOccupied(PokerUser pokerUser) {
    if (pokerUser.getUsername().equals("occupiedUserName")) {
      return true;
    }
    return false;
  }
}
