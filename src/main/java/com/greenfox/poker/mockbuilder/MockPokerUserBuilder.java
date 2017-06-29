package com.greenfox.poker.mockbuilder;


import com.greenfox.poker.model.PokerUser;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MockPokerUserBuilder {

  public PokerUser createMockPokerUser() {
    PokerUser testUser = new PokerUser("TestJeno", "jenopass", "jeno@kovacs.hu", "a");
    testUser.setId(0L);
    return testUser;
  }

  public List<PokerUser> createListOfMockPokerUser() {
    List<PokerUser> topTen = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      topTen.add(createMockPokerUser());
    }
    return topTen;
  }
}
