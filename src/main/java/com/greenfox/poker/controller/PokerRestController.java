package com.greenfox.poker.controller;
import com.greenfox.poker.model.LoginRequest;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class PokerRestController {

  @Autowired
  UserService userService;

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<?> register(@RequestBody @Valid PokerUser userRegister,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return userService.respondToMissingOrInvalidFields(bindingResult);
    }
    return userService.respondToRegister(bindingResult, userRegister);
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return userService.respondToMissingOrInvalidFields(bindingResult);
    }
    return userService.respondToLogin(bindingResult, loginRequest);
  }
}
