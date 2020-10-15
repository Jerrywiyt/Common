package com.lujunyu.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TestJackson {

  public static void main(String[] args) throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    Room room = new Room(1, ImmutableList.of(new Desk("11")));

    System.out.println(mapper.writeValueAsString(room));

    System.out.println(
        mapper.readValue(
            mapper.writeValueAsBytes(room.getDeskList()), new TypeReference<List<Desk>>() {}));
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  static class Room {
    private int id;
    private List<Desk> deskList;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  static class Desk {
    private String name;
  }
}
