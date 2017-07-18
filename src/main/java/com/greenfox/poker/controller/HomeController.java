package com.greenfox.poker.controller;

import com.greenfox.poker.service.Accessible;

import com.greenfox.poker.service.ShowDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin("*")
@Controller
public class HomeController {

  @Autowired
  ShowDownService showDownService;

  @GetMapping("/")
  @ResponseBody
  @Accessible
  public String home() {
    showDownService.choose5CardsOutOf7();
    return "Hello";
  }
}
