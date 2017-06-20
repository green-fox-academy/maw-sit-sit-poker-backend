package com.greenfox.poker.service;


import com.greenfox.poker.controller.UserController;
import com.greenfox.poker.model.PokerUser;
import java.lang.reflect.Method;

public class Security {

  public boolean haveIAccess() {
    UserController userController = new UserController();
    Method[] methods = userController.getClass().getMethods();
    for (Method method : methods) {
      Access annos = method.getAnnotation(Access.class);
      if (annos.restricted()) {
        Class[] params = method.getParameterTypes();
        System.out.println("Here it is Access annotation");
      }
    }
    return true;
  }
}
