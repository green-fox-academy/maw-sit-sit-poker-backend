package com.greenfox.poker.controller;

import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.repository.PokerUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

  @Autowired
  PokerUserRepo pokerUserRepo;

  @GetMapping("/")
  @ResponseBody
  public String home() {
    pokerUserRepo.save(new PokerUser("James Bond","VESPER","james@bond.uk","www.007.com"));
    pokerUserRepo.save(new PokerUser("Ramin James Shamsi Bond","123456VESPER","RaminJames@ShamsiBond.uk","www.006007.com"));
    return "Hello";
  }
}
