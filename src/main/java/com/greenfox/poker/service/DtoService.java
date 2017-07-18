package com.greenfox.poker.service;

import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.repository.PokerUserRepo;
import java.util.HashMap;
import java.util.function.DoubleToIntFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoService {

  private final static Logger logger = Logger.getLogger(DtoService.class.getName());

  HashMap<Long, PokerUserDTO> pokerUserDTOs = new HashMap<>();

  @Autowired
  PokerUserDTO pokerUserDTO;

  @Autowired
  PokerUserRepo pokerUserRepo;

  public boolean hasPlayerEnoughChipsToPlay(long chipsToPlayWith, PokerUserDTO user){
    if (user.getChips() < chipsToPlayWith){
      logger.log(Level.INFO,
              "player has NOT got enough chips to play. Player name: " + user.getUsername());
      return false;
    }
    logger.log(Level.INFO,
            "player has got enough chips to play. Player name: " + user.getUsername());
    return true;
  }

  public void deductChipsFromAvailableChips(long chipsToPlayWith, long playerId){
    long chipsAvailableToDTOAfterJoiningTable = pokerUserDTOs.get(playerId).getChips() - chipsToPlayWith;
    pokerUserDTOs.get(playerId).setChips(chipsAvailableToDTOAfterJoiningTable);
  }

  public PokerUserDTO makePokerUserDTO(PokerUser pUser) {
    pokerUserDTO.setId(pUser.getId());
    pokerUserDTO.setUsername(pUser.getUsername());
    pokerUserDTO.setAvatar(pUser.getAvatar());
    pokerUserDTO.setChips(pUser.getChips());
    pokerUserDTOs.put(pokerUserDTO.getId(), pokerUserDTO);
    return pokerUserDTO;
  }

  public void removePokerUserDTO(long playerId){
    pokerUserDTOs.remove(playerId);
    logger.log(Level.INFO,
            "Player is removed from the table. Player id: " + playerId);
  }

}
