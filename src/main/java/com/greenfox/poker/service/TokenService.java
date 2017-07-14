package com.greenfox.poker.service;

import com.greenfox.poker.model.PokerUser;

import com.greenfox.poker.model.PokerUserDTO;
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
  DtoService dtoService;

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
        .signWith(SignatureAlgorithm.HS256, this.key)
        .compact();
    return token;
  }

  private Claims getClaimsFromToken(String token) {
    Claims claims;
    claims = Jwts.parser()
        .setSigningKey(this.key)
        .parseClaimsJws(token)
        .getBody();
    return claims;
  }


  private long getIdFromToken(String token) {
    long id;
    try {
      Claims claims = getClaimsFromToken(token);
      id = new Long((Integer) claims.get("id"));
    } catch (MissingClaimException e) {
      id = -1L;
    }
    return id;
  }

  public PokerUserDTO getPokerUserDTOFromToken(String token) {
    long idFromToken = getIdFromToken(token);
    return dtoService.pokerUserDTOs.get(idFromToken);
  }
}

