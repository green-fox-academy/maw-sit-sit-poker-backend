package com.greenfox.poker.service;

import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.repository.PokerUserRepo;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoService {

  HashMap<Long, PokerUserDTO> pokerUserDTOs = new HashMap<>();

  @Autowired
  PokerUserDTO pokerUserDTO;

  @Autowired
  PokerUserRepo pokerUserRepo;

  public boolean hasPlayerEnoughChipsToPlay(long chipsToPlayWith, PokerUserDTO user){
    if (user.getChips() < chipsToPlayWith){
      return false;
    }
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
  }
}
