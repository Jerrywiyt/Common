package com.lujunyu.test;


import java.sql.SQLException;

public class Test {
	public static void main(String args[]) throws SQLException, ClassNotFoundException {
		byte[] b = change("5b42acf317f8060854e00200007870000001d80b0b0a076");
		System.out.println(new String(b));
	}
	private static byte[] change(String s) {
		byte[] b = new byte[s.length()/2];
		for (int i = 0; i < s.length(); i = i + 2) {
			char h = s.charAt(i);
			char l = s.charAt(i + 1);
			b[i / 2] = (byte) (h << 4 + l);
		}
		return b;
	}

	private static byte ch(char c) {
		if (c == 'a') {
			return 10;
		}
		if (c == 'b') {
			return 11;
		}
		if (c == 'c') {
			return 12;
		}
		if (c == 'd') {
			return 13;
		}
		if (c == 'e') {
			return 14;
		}
		if (c == 'f') {
			return 15;
		}
		return Byte.parseByte(c + "");
	}
}
