package com.lujunyu.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

public class TestJackson {

  public static void main(String[] args) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    System.out.println(mapper.readTree("{}"));

    System.out.println(mapper.writeValueAsString(ImmutableSet.of("a", "b")));

    System.out.println(mapper.readValue("true", new TypeReference<Boolean>() {}));
  }
}
