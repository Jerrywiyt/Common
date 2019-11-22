package com.lujunyu.algorithm.pratice;

import java.util.ArrayList;
import java.util.List;

/**
 * airbnb现场coding第二题。
 * 打印倒三角。
 *
 * n=1
 * \--/
 *  \/
 *
 * n=2
 * \--/\--/
 *  \/  \/
 *   \--/
 *    \/
 *
 * n=3
 * \--/\--/\--/\--/
 *  \/  \/  \/  \/
 *   \--/    \--/
 *    \/      \/
 *     \--/\--/
 *      \/  \/
 *       \--/
 *        \/
 *
 * n=4
 * \--/\--/\--/\--/\--/\--/\--/\--/
 *  \/  \/  \/  \/  \/  \/  \/  \/
 *   \--/    \--/    \--/    \--/
 *    \/      \/      \/      \/
 *     \--/\--/        \--/\--/
 *      \/  \/          \/  \/
 *       \--/            \--/
 *        \/              \/
 *         \--/\--/\--/\--/
 *          \/  \/  \/  \/
 *           \--/    \--/
 *            \/      \/
 *             \--/\--/
 *              \/  \/
 *               \--/
 *                \/
 *
 */
public class 打印倒三角 {
    public static void main(String args[]){
        print(1);
        print(2);
        print(3);
        print(4);
        print(5);
    }

    /**
     * 可以采用递归方式，备忘录方式，尾递归方式等。
     * @param n
     */
    private static void print(int n) {
        List<String> res = getLine(n);
        for (String line : res) {
            System.out.println(line);
        }
    }

    private static List<String> getLine(int n) {
        if (n == 1) {
            List<String> res = new ArrayList<>();
            res.add("\\--/");
            res.add(" \\/");
            return res;
        } else {
            List<String> line1 = getLine(n - 1);
            List<String> newLine = new ArrayList<>(line1.size() * 2);
            //第一层。
            for (int i = 0; i < line1.size(); i++) {
                newLine.add(line1.get(i) + getSpace(2 * i) + line1.get(i).trim());
            }

            //第二层。
            int base = line1.get(0).length() / 2;
            for (int i = 0; i < line1.size(); i++) {
                newLine.add(getSpace(base + i) + line1.get(i).trim());
            }
            return newLine;
        }
    }

    private static String getSpace(int n) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            res.append(" ");
        }
        return res.toString();
    }
}
