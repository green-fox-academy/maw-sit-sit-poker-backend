package com.greenfox.poker.model;

public class StatusError {

  private String status = "error";
  private String message;

  public StatusError() {
  }

  public StatusError(String message) {
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
