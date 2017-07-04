package com.greenfox.poker.service;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
public class LoggingService {

  public void getRequestHeaderAndItsValue(RequestHeader requestHeader, Object[] argsList, int argIndex) {
    System.out.println(requestHeader.value() + " = " + argsList[argIndex]);
  }
}
