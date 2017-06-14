package com.greenfox.poker.service;

import com.greenfox.poker.model.LoginRequest;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.RegisterResponse;
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

  public RegisterResponse mockRespondToSuccessfulRegisterOrLogin() {
    return new RegisterResponse("success", "ABC123", 4321);
  }

  public ResponseEntity<?> respondToMissingOrInvalidFields(BindingResult bindingResult) {
    List<String> listOfMissingFields = new ArrayList<>();
    String missingFields = new String();
      for (FieldError fielderror : bindingResult.getFieldErrors()) {
        listOfMissingFields.add(fielderror.getField());
      }
      missingFields = "Missing paramater(s): " + listOfMissingFields.toString();
      return new ResponseEntity(new StatusError("fail", missingFields ), HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<?> respondToRegister(BindingResult bindingResult, PokerUser pokerUser) {
    String occupiedField = new String();
    if (!bindingResult.hasErrors() && isEmailOccupied(pokerUser)){
      return new ResponseEntity(new StatusError("fail", occupiedField + "email address already exists"),
          HttpStatus.CONFLICT);
    } else if (!bindingResult.hasErrors() && isUsernameOccupied(pokerUser)){
      return new ResponseEntity(new StatusError("fail", occupiedField + "username already exists"),
          HttpStatus.CONFLICT);
    } else return new ResponseEntity(mockRespondToSuccessfulRegisterOrLogin(), HttpStatus.OK);
  }

  public ResponseEntity<?> respondToLogin(BindingResult bindingResult,LoginRequest loginRequest) {
    if (!bindingResult.hasErrors() && (!loginRequest.getUsername().equals("Bond") || !loginRequest
        .getPassword().equals("password123"))) {
      return new ResponseEntity(new StatusError("fail", "invalid username or password"),
          HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity(mockRespondToSuccessfulRegisterOrLogin(), HttpStatus.OK);
  }

  public boolean isEmailOccupied(PokerUser pokerUser){
    if (pokerUser.getEmail().equals("occupied@email.com")) {
      return true;
    }
    return false;
  }

  public boolean isUsernameOccupied(PokerUser pokerUser){
    if (pokerUser.getUsername().equals("occupiedUserName")) {
      return true;
    }
    return false;
  }
}
