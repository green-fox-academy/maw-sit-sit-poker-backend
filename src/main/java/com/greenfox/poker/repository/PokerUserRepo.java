package com.greenfox.poker.repository;


import com.greenfox.poker.model.PokerUser;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

public interface PokerUserRepo extends CrudRepository<PokerUser, Long> {
  List<PokerUser> findAll();
}
