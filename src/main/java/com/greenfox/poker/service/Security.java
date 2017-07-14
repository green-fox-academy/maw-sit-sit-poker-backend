package com.greenfox.poker.service;


import com.greenfox.poker.controller.UserController;
import java.lang.reflect.Method;

public class Security {

  public boolean haveIAccess() {
    UserController userController = new UserController();
    Method[] methods = userController.getClass().getMethods();
    for (Method method : methods) {
      Accessible annos = method.getAnnotation(Accessible.class);
      if (annos.restricted()) {
        Class[] params = method.getParameterTypes();
        System.out.println("Here it is Accessible annotation");
      }
    }
    return true;
  }
}
