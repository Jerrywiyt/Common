package com.lujunyu.lombok;

import java.util.HashMap;
import lombok.val;
import lombok.var;
import org.junit.Test;

public class TestValVar {

  @Test
  public void testVal() {
    val map = new HashMap<String, String>();
  }

  @Test
  public void testVar() {
    var map = new HashMap<String, String>();
  }
}
