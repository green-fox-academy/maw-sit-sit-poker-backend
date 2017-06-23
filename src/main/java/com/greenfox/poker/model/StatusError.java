package com.greenfox.poker.model;

public class StatusError implements ResponseType {

  private String result;
  private String message;

  public StatusError() {
  }

  public StatusError(String message) {
    this.message = message;
  }

  public String getResult() {
    return result;
  }

  public StatusError(String result, String message) {
    this.result = result;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
