package com.greenfox.poker.controller;

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
public class RestController {

  @Autowired
  UserService userService;

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<?> register(@RequestBody @Valid PokerUser userRegister,
          BindingResult bindingResult) {
    return userService.createResponseJson(bindingResult);
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<?> login(@RequestBody @Valid PokerUser userLogin,
          BindingResult bindingResult) {
    return userService.createResponseJson(bindingResult);
  }

  public void fatherfucker() {
  }
}
