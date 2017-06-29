package com.greenfox.poker.model;

import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class PlayerAction {

  @NotNull
  Action action;
  long value;

  public PlayerAction() {
  }

  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }
}
