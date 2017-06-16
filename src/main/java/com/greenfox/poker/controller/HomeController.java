package com.greenfox.poker.controller;

import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.repository.PokerUserRepo;
import com.greenfox.poker.service.GamePlayerService;
import com.greenfox.poker.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {


  @GetMapping("/")
  @ResponseBody
  public String home() {

    return "Hello";
  }
}
