package com.greenfox.poker.repository;


import com.greenfox.poker.model.Game;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface GameRepo extends CrudRepository<Game, Long> {
  List<Game> findAll();
  List<Game> findAllByOrderByBigBlindDesc();
  List<Game> findAllGamesOrderByBigBlind();

}
