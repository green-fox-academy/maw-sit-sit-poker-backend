package com.greenfox.poker.mockbuilder;

import com.greenfox.poker.model.PokerUser;

public class MockPokerUserBuilder {

  PokerUser mockPokerUser;

  public MockPokerUserBuilder(){
    mockPokerUser = new PokerUser("Pisti", "password123", "pisti@pisti.com", "avatarurl");
  }

  public MockPokerUserBuilder setUserName(String name){
    mockPokerUser.setUsername(name);
    return this;
  }

  public MockPokerUserBuilder setPword(String password){
    mockPokerUser.setPassword(password);
    return this;
  }

  public MockPokerUserBuilder setMail(String email){
    mockPokerUser.setEmail(email);
    return this;
  }

  public MockPokerUserBuilder setAvaTar(String avatar){
    mockPokerUser.setAvatar(avatar);
    return this;
  }

  public PokerUser build(){
    return mockPokerUser;
  }
}
