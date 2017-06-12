package com.greenfox.poker.controller;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class RestController {

  @Autowired
  UserService userService;

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<?> register(@RequestBody @Valid PokerUser userRegister) {
    if(userService.validateUserRegistration(userRegister)){
      return new ResponseEntity(userService.registerNewUser(userRegister), HttpStatus.OK );
    }else
    return new ResponseEntity(userService.respondToRegisterError("mivan"), HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<?> login(@RequestBody @Valid PokerUser userLogin) {
    if(userService.validateUserLogin(userLogin)){
      return new ResponseEntity(userService.loginUser(userLogin), HttpStatus.OK );
    }else
      return new ResponseEntity(userService.respondToLoginError("login elbaszva"), HttpStatus.BAD_REQUEST);
  }
}
