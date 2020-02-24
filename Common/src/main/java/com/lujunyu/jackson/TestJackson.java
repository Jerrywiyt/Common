package com.lujunyu.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJackson {

  public static void main(String[] args) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    System.out.println(mapper.readTree("{}"));
  }
}
