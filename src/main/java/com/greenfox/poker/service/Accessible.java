package com.greenfox.poker.service;

import java.lang.annotation.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Accessible {
  boolean restricted() default false;
}
