package com.greenfox.poker.service;

import com.greenfox.poker.model.PokerUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import io.jsonwebtoken.lang.Assert;
import java.security.Key;
import java.util.*;
import java.lang.*;
import org.springframework.stereotype.Component;

@Component
public class TokenService {

  private Key key;

  public String generateToken(PokerUser pokerUser) {
    HashMap<String, Object> claims = new HashMap<>();
    claims.put("id", pokerUser.getId());
    claims.put("username", pokerUser.getUsername());
    return generateToken(claims);
  }

  private String generateToken(HashMap<String, Object> claims) {
    this.key = MacProvider.generateKey();
    String token = Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS256, key)
        .compact();
    return token;
  }

  private Claims getClaimFromToken(String token) {
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
      Claims claims = getClaimFromToken(token);
      username = (String) claims.get("username");
    } catch (MissingClaimException e) {
      username = null;
    }
    return username;
  }

  public String getIdFromToken(String token) {
    String id;
    try {
      Claims claims = getClaimFromToken(token);
      id = (String) claims.get("id");
    } catch (MissingClaimException e) {
      id = null;
    }
    return id;
  }
}

