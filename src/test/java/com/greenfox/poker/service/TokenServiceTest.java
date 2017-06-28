package com.greenfox.poker.service;

import com.greenfox.poker.PokergameApplication;
import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.repository.PokerUserRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PokergameApplication.class)
public class TokenServiceTest {

  @Autowired
  PokerUserRepo pokerUserRepo;

  @Autowired
  TokenService tokenService;

  private PokerUser createTestPokerUser() {

    PokerUser testPokerUser = new PokerUser();
    testPokerUser.setUsername("TestBond");
    testPokerUser.setPassword("BodPassword");
    testPokerUser.setEmail("james@Bond.uk");
    pokerUserRepo.save(testPokerUser);
    return testPokerUser;
  }

  private void deleteTestPokerUser() {
    long testUserId = pokerUserRepo.findByUsername("TestBond").getId();
    pokerUserRepo.delete(testUserId);
  }


  @Test
  public void generateTokenTest() {
    PokerUser testPokerUser = createTestPokerUser();
    String token = tokenService.generateToken(testPokerUser);
    Assert.assertNotNull(token);
    deleteTestPokerUser();
  }

  @Test
  public void getTokenFromPokerUserTest() {
    PokerUser testPokerUser = createTestPokerUser();
    String token = tokenService.generateToken(testPokerUser);
    PokerUser retrievedPokerUserFromToken = tokenService.getPokerUserFromToken(token);
    Assert.assertEquals(retrievedPokerUserFromToken.getToken(), testPokerUser.getToken());
    deleteTestPokerUser();
  }

  @Test
  public void getUsernameFromPokerUserTest() {
    PokerUser testPokerUser = createTestPokerUser();
    String token = tokenService.generateToken(testPokerUser);
    PokerUser retrievedPokerUserFromToken = tokenService.getPokerUserFromToken(token);
    Assert.assertEquals(retrievedPokerUserFromToken.getUsername(), testPokerUser.getUsername());
    deleteTestPokerUser();
  }

  @Test
  public void getPasswordFromPokerUserTest() {
    PokerUser testPokerUser = createTestPokerUser();
    String token = tokenService.generateToken(testPokerUser);
    PokerUser retrievedPokerUserFromToken = tokenService.getPokerUserFromToken(token);
    Assert.assertEquals(retrievedPokerUserFromToken.getPassword(), testPokerUser.getPassword());
    deleteTestPokerUser();
  }

  @Test
  public void getEmailFromPokerUserTest() {
    PokerUser testPokerUser = createTestPokerUser();
    String token = tokenService.generateToken(testPokerUser);
    PokerUser retrievedPokerUserFromToken = tokenService.getPokerUserFromToken(token);
    Assert.assertEquals(retrievedPokerUserFromToken.getEmail(), testPokerUser.getEmail());
    deleteTestPokerUser();
  }
}