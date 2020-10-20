package com.lujunyu.lombok;

import com.google.common.base.Preconditions;
import java.util.Objects;
import lombok.NonNull;
import org.junit.Test;

public class TestNonNull {

  /** wil throw: java.lang.NullPointerException: param is marked @NonNull but is null */
  @Test
  public void testNonNull1() {
    test1(null);
  }

  private void test1(@NonNull String param) {
    //    Preconditions.checkNotNull(param);
    //    Objects.requireNonNull(param);
  }
}
