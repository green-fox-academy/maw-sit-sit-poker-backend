package com.greenfox.poker.repository;


import com.greenfox.poker.model.GamePlayer;
import com.greenfox.poker.model.PokerUser;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface GamePlayerRepository extends CrudRepository<GamePlayer, Long> {
  List<GamePlayer> findAll();
}
