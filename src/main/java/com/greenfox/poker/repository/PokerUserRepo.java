package com.greenfox.poker.repository;


import com.greenfox.poker.model.PokerUser;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PokerUserRepo extends CrudRepository<PokerUser, Long> {

  List<PokerUser> findAll();

  List<PokerUser> findTop10ByOrderByChipsDesc();
  PokerUser findByUsername(String username);

  boolean existsByUsername(String username);
  boolean existsByPassword(String password);
  boolean existsByEmail(String email);
  boolean existsByToken(String token);
}
