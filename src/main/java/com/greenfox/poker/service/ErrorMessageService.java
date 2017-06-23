package com.greenfox.poker.service;


import com.greenfox.poker.model.ResponseType;
import com.greenfox.poker.model.StatusError;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class ErrorMessageService {

  public ResponseType respondToMissingParameters(BindingResult bindingResult) {
    List<String> missing = new ArrayList<>();
    String missingFields = new String();
    for (FieldError fielderror : bindingResult.getFieldErrors()) {
      missing.add(fielderror.getField());
    }
    System.out.println(missingFields);
    missingFields = "Missing parameter(s): " + missing.stream().collect(Collectors.joining(", ")) + "!";
    return new StatusError("fail", missingFields);
  }

}
