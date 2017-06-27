package com.greenfox.poker.service;

import com.greenfox.poker.model.PokerUser;
import com.greenfox.poker.model.PokerUserDTO;
import com.greenfox.poker.repository.PokerUserRepo;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoService {

  HashMap<Long, PokerUserDTO> userDTOHashMap = new HashMap<>();

  @Autowired
  PokerUserDTO pokerUserDTO;

  @Autowired
  PokerUserRepo pokerUserRepo;

  public boolean hasPlayerEnoughChipsToPlay(long chipsToPlayWith, long userId){
    pokerUserDTO = userDTOHashMap.get(userId);
    if (pokerUserDTO.getChips() < chipsToPlayWith){
      return false;
    }
    return true;
  }

  public void deductChipsFromAvailableChips(long chipsToPlayWith, long playerId){
    long chipsAvailableToDTOAfterJoiningTable = userDTOHashMap.get(playerId).getChips() - chipsToPlayWith;
    userDTOHashMap.get(playerId).setChips(chipsAvailableToDTOAfterJoiningTable);
  }

  public PokerUserDTO makePokerUserDTO(long id) {
    PokerUser pokerUser = pokerUserRepo.findOne(id);
    pokerUserDTO.setId(id);
    pokerUserDTO.setUsername(pokerUser.getUsername());
    pokerUserDTO.setAvatar(pokerUser.getAvatar());
    pokerUserDTO.setChips(pokerUser.getChips());
    userDTOHashMap.put(pokerUserDTO.getId(), pokerUserDTO);
    return pokerUserDTO;
  }
}
