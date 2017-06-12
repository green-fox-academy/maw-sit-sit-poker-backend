package com.greenfox.poker.controller;

import com.greenfox.poker.model.UserRegister;
import com.greenfox.poker.service.ResponseDataObject;
import com.greenfox.poker.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class RestController {

  @Autowired
  UserService userService;

  @Autowired
  ResponseDataObject responseDataObject;


  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseDataObject login(@RequestBody @Valid UserRegister userRegister) {
    return responseDataObject;
  }
}
