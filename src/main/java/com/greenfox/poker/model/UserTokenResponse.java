package com.greenfox.poker.model;

import org.springframework.stereotype.Component;

@Component
public class UserTokenResponse {

  private String result;
  private String token;
  private long id;

  public UserTokenResponse() {
  }

  public UserTokenResponse(String result, String token, long id) {
    this.result = result;
    this.token = token;
    this.id = id;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}



