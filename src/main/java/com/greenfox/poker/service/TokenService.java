package com.greenfox.poker.service;

import com.greenfox.poker.model.PokerUser;

import com.greenfox.poker.repository.PokerUserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.*;
import java.lang.*;
import javax.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenService {

  @Autowired
  PokerUserRepo pokerUserRepo;

  private Key key;

  public String generateToken(PokerUser pokerUser) {
    HashMap<String, Object> claims = new HashMap<>();
    claims.put("id", pokerUser.getId());
    claims.put("username", pokerUser.getUsername());
    String token = generateTokenByClaims(claims);
    return token;
  }

  private String generateTokenByClaims(HashMap<String, Object> claims) {
    this.key = MacProvider.generateKey();
    String token = Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS256, key)
        .compact();
    return token;
  }

  private Claims getClaimsFromToken(String token) {
    Claims claims;
    claims = Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(token)
        .getBody();
    return claims;
  }

  public String getUsernameFromToken(String token) {
    String username;
    try {
      Claims claims = getClaimsFromToken(token);
      username = (String) claims.get("username");
    } catch (MissingClaimException e) {
      username = null;
    }
    return username;
  }

  public long getIdFromToken(String token) {
    long id;
    try {
      Claims claims = getClaimsFromToken(token);
      id = (Integer) claims.get("id");
    } catch (MissingClaimException e) {
      id = -1L;
    }
    return id;
  }

  public PokerUser getPokerUserFromToken(String token) {
    long idFromToken = getIdFromToken(token);
    PokerUser pokerUserFromToken = pokerUserRepo.findOne(idFromToken);
    return pokerUserFromToken;
  }
}

