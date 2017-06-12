package com.greenfox.poker.service;

import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.RegisterResponse;
import com.greenfox.poker.model.StatusError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class UserService {

  public RegisterResponse mockResponse() {
    return new RegisterResponse(1, "Bond", "james@bond.uk", null);
  }

  public RegisterResponse registerNewUser(PokerUser newUserToRegister) {
    return new RegisterResponse(newUserToRegister.getId(), newUserToRegister.getUsername(),
        newUserToRegister.getEmail(), newUserToRegister.getAvatar());
  }

  public ResponseEntity<?> createResponseJson(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String missingFields = new String();
      for (FieldError fielderror : bindingResult.getFieldErrors()) {
        missingFields += fielderror.getField() + ", ";
      }
      missingFields =
          "Missing paramater(s): " + missingFields.substring(0, missingFields.length() - 2) + "!";
      return new ResponseEntity(new StatusError(missingFields), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity(mockResponse(), HttpStatus.OK);
  }
}
