package com.greenfox.poker.model;


public enum Action {
  RAISE("raise"), CHECK("check"), CALL("call"), FOLD("fold"), STANDUP("standup");

  private final String actionName;

  Action(String s) {
    this.actionName = s;
  }

  public String getActionName() {
    return actionName;
  }

}
