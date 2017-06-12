package com.greenfox.poker.model;

import com.fasterxml.jackson.annotation.JsonInclude;


public class ResponseDataObject {

  private User user;
  private Status status;

  public ResponseDataObject() {
  }

  public ResponseDataObject(User user) {
    this.user = user;
    this.status = null;
  }

  public ResponseDataObject(User user, Status status) {
    this.user = user;
    this.status = status;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
