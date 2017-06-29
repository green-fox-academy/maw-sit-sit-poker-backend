package com.greenfox.poker.mockbuilder;

import com.greenfox.poker.model.PokerUser;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class MockPokerUserBuilder {

  PokerUser mockPokerUser;

  public MockPokerUserBuilder() {
    mockPokerUser = new PokerUser("Pisti", "password123", "pisti@pisti.com", "avatarurl");
    mockPokerUser.setId(1l);
  }

  public MockPokerUserBuilder setUserName(String name) {
    mockPokerUser.setUsername(name);
    return this;
  }

  public MockPokerUserBuilder setPword(String password) {
    mockPokerUser.setPassword(password);
    return this;
  }

  public MockPokerUserBuilder setMail(String email) {
    mockPokerUser.setEmail(email);
    return this;
  }

  public MockPokerUserBuilder setAvaTar(String avatar) {
    mockPokerUser.setAvatar(avatar);
    return this;
  }

  public PokerUser build() {
    return mockPokerUser;
  }

  public List<PokerUser> createListOfMockPokerUser () {
      List<PokerUser> topTen = new ArrayList<>();
      for (int i = 0; i < 10; i++) {
        topTen.add(build());
      }
      return topTen;
    }
  }
