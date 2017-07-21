package com.greenfox.poker.controller;

import com.greenfox.poker.service.Accessible;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin("*")
@Controller
public class HomeController {

  @GetMapping("/")
  @ResponseBody
  @Accessible
  public String home() {
    return "Hello";
  }
}
