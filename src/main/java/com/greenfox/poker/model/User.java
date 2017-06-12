package com.greenfox.poker.model;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;

public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotBlank(message = "you cannot leave this field blank")
  private String username;

  @NotBlank(message = "you cannot leave this field blank")
  private String password;

  @NotBlank(message = "you cannot leave this field blank")
  private String email;

  private String avatar;
  private long cash;

}
