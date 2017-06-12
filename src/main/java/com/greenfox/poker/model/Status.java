package com.greenfox.poker.model;

import org.springframework.stereotype.Component;

@Component
public class Status {

  private String status;
  private String message;

  public Status() {
    this.status = "error";
  }

  public Status(String message) {
    this.message = message;
    this.status = "error";
  }
}
