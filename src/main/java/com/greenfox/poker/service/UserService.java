package com.greenfox.poker.service;

import com.greenfox.poker.model.LoginRequest;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.ResponseType;
import com.greenfox.poker.model.UserTokenResponse;
import com.greenfox.poker.model.StatusError;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class UserService {

  public ResponseType mockResponseToSuccessfulRegisterOrLogin() {
    return new UserTokenResponse("success", "ABC123", 4321);
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
