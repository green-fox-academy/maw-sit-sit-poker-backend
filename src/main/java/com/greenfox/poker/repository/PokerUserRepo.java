package com.greenfox.poker.repository;


import com.greenfox.poker.model.PokerUser;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PokerUserRepo extends CrudRepository<PokerUser, Long> {

  List<PokerUser> findAll();

  List<PokerUser> findTop10ByOrderByChipsDesc();
  List<PokerUser> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}


