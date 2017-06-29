package com.greenfox.poker.mockbuilder;


import com.greenfox.poker.model.PokerUser;
import org.springframework.stereotype.Component;

@Component
public class MockPokerUserBuilder {


  public PokerUser createMockPokerUser() {
    PokerUser testUser = new PokerUser("TestJeno", "jenopass", "jeno@kovacs.hu", "a");
    testUser.setId(0L);
    return testUser;
  }


}
