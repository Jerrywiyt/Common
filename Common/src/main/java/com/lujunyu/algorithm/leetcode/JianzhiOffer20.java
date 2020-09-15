package com.lujunyu.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

public class JianzhiOffer20 {

  static class Solution {
    private static Map<State, Map<CharType, State>> transfer = new HashMap<>();

    static {
      Map<CharType, State> init = new HashMap<>();
      init.put(CharType.CHAR_SPACE, State.STATE_INIT);
      init.put(CharType.CHAR_SIGN, State.STATE_INT_SIGN);
      init.put(CharType.CHAR_NUMBER, State.STATE_INTEGER);
      init.put(CharType.CHAR_POINT, State.STATE_POINT_WITHOUT_INTEGER);
      transfer.put(State.STATE_INIT, init);

      Map<CharType, State> intSign = new HashMap<>();
      intSign.put(CharType.CHAR_POINT, State.STATE_POINT_WITHOUT_INTEGER);
      intSign.put(CharType.CHAR_NUMBER, State.STATE_INTEGER);
      transfer.put(State.STATE_INT_SIGN, intSign);

      Map<CharType, State> integerState = new HashMap<>();
      integerState.put(CharType.CHAR_NUMBER, State.STATE_INTEGER);
      integerState.put(CharType.CHAR_POINT, State.STATE_POINT);
      integerState.put(CharType.CHAR_EXP, State.STATE_EXP);
      integerState.put(CharType.CHAR_SPACE, State.STATE_END);
      transfer.put(State.STATE_INTEGER, integerState);

      Map<CharType, State> pointState = new HashMap<>();
      pointState.put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
      pointState.put(CharType.CHAR_SPACE, State.STATE_END);
      pointState.put(CharType.CHAR_EXP, State.STATE_EXP);
      transfer.put(State.STATE_POINT, pointState);

      Map<CharType, State> pointWithoutInteger = new HashMap<>();
      pointWithoutInteger.put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
      transfer.put(State.STATE_POINT_WITHOUT_INTEGER, pointWithoutInteger);

      Map<CharType, State> fractionState = new HashMap<>();
      fractionState.put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
      fractionState.put(CharType.CHAR_EXP, State.STATE_EXP);
      fractionState.put(CharType.CHAR_SPACE, State.STATE_END);
      transfer.put(State.STATE_FRACTION, fractionState);

      Map<CharType, State> expState = new HashMap<>();
      expState.put(CharType.CHAR_NUMBER, State.STATE_EXP_INTEGER);
      expState.put(CharType.CHAR_SIGN, State.STATE_EXP_SIGN);
      transfer.put(State.STATE_EXP, expState);

      Map<CharType, State> expSignState = new HashMap<>();
      expSignState.put(CharType.CHAR_NUMBER, State.STATE_EXP_INTEGER);
      transfer.put(State.STATE_EXP_SIGN, expSignState);

      Map<CharType, State> expIntegerState = new HashMap<>();
      expIntegerState.put(CharType.CHAR_NUMBER, State.STATE_EXP_INTEGER);
      expIntegerState.put(CharType.CHAR_SPACE, State.STATE_END);
      transfer.put(State.STATE_EXP_INTEGER, expIntegerState);

      Map<CharType, State> endState = new HashMap<>();
      endState.put(CharType.CHAR_SPACE, State.STATE_END);
      transfer.put(State.STATE_END, endState);
    }

    enum State {
      STATE_INIT,
      STATE_INTEGER,
      STATE_INT_SIGN,
      STATE_POINT,
      STATE_POINT_WITHOUT_INTEGER,
      STATE_FRACTION,
      STATE_EXP,
      STATE_EXP_SIGN,
      STATE_EXP_INTEGER,
      STATE_END
    }

    enum CharType {
      CHAR_SPACE,
      CHAR_NUMBER,
      CHAR_POINT,
      CHAR_EXP,
      CHAR_SIGN,
      CHAR_ILLEGAL
    }

    public boolean isNumber(String s) {
      if (s == null || s.length() == 0) {
        return false;
      }
      State state = State.STATE_INIT;

      for (char c : s.toCharArray()) {
        CharType charType = toCharType(c);
        if (!transfer.get(state).containsKey(charType)) {
          return false;
        } else {
          state = transfer.get(state).get(charType);
        }
      }
      return state == State.STATE_END
          || state == State.STATE_POINT
          || state == State.STATE_INTEGER
          || state == State.STATE_FRACTION
          || state == State.STATE_EXP_INTEGER;
    }

    private CharType toCharType(char c) {
      if (c == 'e' || c == 'E') {
        return CharType.CHAR_EXP;
      }
      if (c == '.') {
        return CharType.CHAR_POINT;
      }

      if (c == '+' || c == '-') {
        return CharType.CHAR_SIGN;
      }

      if (c >= '0' && c <= '9') {
        return CharType.CHAR_NUMBER;
      }

      if (c == ' ') {
        return CharType.CHAR_SPACE;
      }
      return CharType.CHAR_ILLEGAL;
    }
  }

  public static void main(String[] args) {
    System.out.println(new Solution().isNumber("  221.1E+12312  "));
  }
}
