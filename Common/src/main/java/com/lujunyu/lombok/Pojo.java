package com.lujunyu.lombok;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class Pojo {
  private String name;
  private LocalDate birthDate;

  public static void main(String[] args) throws JsonProcessingException {
    Pojo pojo = new Pojo();
    pojo.setName("ljy");
    System.out.println(JSON.toJSONString(pojo));

    System.out.println(new ObjectMapper().writeValueAsString(pojo));
  }
}
