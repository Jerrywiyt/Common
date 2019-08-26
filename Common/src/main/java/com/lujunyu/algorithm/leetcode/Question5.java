package com.lujunyu.algorithm.leetcode;

//给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
//
// 示例 1：
//
// 输入: "babad"
//输出: "bab"
//注意: "aba" 也是一个有效答案。
//
//
// 示例 2：
//
// 输入: "cbbd"
//输出: "bb"
//
//


class Question5 {
    public static void main(String args[]) {
        Question5 question5 = new Question5();
        System.out.println(question5.longestPalindrome1("jrjnbctoqgzimtoklkxcknwmhiztomaofwwzjnhrijwkgmwwuazcowskjhitejnvtblqyepxispasrgvgzqlvrmvhxusiqqzzibcyhpnruhrgbzsmlsuacwptmzxuewnjzmwxbdzqyvsjzxiecsnkdibudtvthzlizralpaowsbakzconeuwwpsqynaxqmgngzpovauxsqgypinywwtmekzhhlzaeatbzryreuttgwfqmmpeywtvpssznkwhzuqewuqtfuflttjcxrhwexvtxjihunpywerkktbvlsyomkxuwrqqmbmzjbfytdddnkasmdyukawrzrnhdmaefzltddipcrhuchvdcoegamlfifzistnplqabtazunlelslicrkuuhosoyduhootlwsbtxautewkvnvlbtixkmxhngidxecehslqjpcdrtlqswmyghmwlttjecvbueswsixoxmymcepbmuwtzanmvujmalyghzkvtoxynyusbpzpolaplsgrunpfgdbbtvtkahqmmlbxzcfznvhxsiytlsxmmtqiudyjlnbkzvtbqdsknsrknsykqzucevgmmcoanilsyyklpbxqosoquolvytefhvozwtwcrmbnyijbammlzrgalrymyfpysbqpjwzirsfknnyseiujadovngogvptphuyzkrwgjqwdhtvgxnmxuheofplizpxijfytfabx"));
        System.out.println(question5.longestPalindrome2("jrjnbctoqgzimtoklkxcknwmhiztomaofwwzjnhrijwkgmwwuazcowskjhitejnvtblqyepxispasrgvgzqlvrmvhxusiqqzzibcyhpnruhrgbzsmlsuacwptmzxuewnjzmwxbdzqyvsjzxiecsnkdibudtvthzlizralpaowsbakzconeuwwpsqynaxqmgngzpovauxsqgypinywwtmekzhhlzaeatbzryreuttgwfqmmpeywtvpssznkwhzuqewuqtfuflttjcxrhwexvtxjihunpywerkktbvlsyomkxuwrqqmbmzjbfytdddnkasmdyukawrzrnhdmaefzltddipcrhuchvdcoegamlfifzistnplqabtazunlelslicrkuuhosoyduhootlwsbtxautewkvnvlbtixkmxhngidxecehslqjpcdrtlqswmyghmwlttjecvbueswsixoxmymcepbmuwtzanmvujmalyghzkvtoxynyusbpzpolaplsgrunpfgdbbtvtkahqmmlbxzcfznvhxsiytlsxmmtqiudyjlnbkzvtbqdsknsrknsykqzucevgmmcoanilsyyklpbxqosoquolvytefhvozwtwcrmbnyijbammlzrgalrymyfpysbqpjwzirsfknnyseiujadovngogvptphuyzkrwgjqwdhtvgxnmxuheofplizpxijfytfabx"));
        System.out.println(question5.longestPalindrome2("abc534cba"));
        System.out.println(question5.longestPalindrome3("jrjnbctoqgzimtoklkxcknwmhiztomaofwwzjnhrijwkgmwwuazcowskjhitejnvtblqyepxispasrgvgzqlvrmvhxusiqqzzibcyhpnruhrgbzsmlsuacwptmzxuewnjzmwxbdzqyvsjzxiecsnkdibudtvthzlizralpaowsbakzconeuwwpsqynaxqmgngzpovauxsqgypinywwtmekzhhlzaeatbzryreuttgwfqmmpeywtvpssznkwhzuqewuqtfuflttjcxrhwexvtxjihunpywerkktbvlsyomkxuwrqqmbmzjbfytdddnkasmdyukawrzrnhdmaefzltddipcrhuchvdcoegamlfifzistnplqabtazunlelslicrkuuhosoyduhootlwsbtxautewkvnvlbtixkmxhngidxecehslqjpcdrtlqswmyghmwlttjecvbueswsixoxmymcepbmuwtzanmvujmalyghzkvtoxynyusbpzpolaplsgrunpfgdbbtvtkahqmmlbxzcfznvhxsiytlsxmmtqiudyjlnbkzvtbqdsknsrknsykqzucevgmmcoanilsyyklpbxqosoquolvytefhvozwtwcrmbnyijbammlzrgalrymyfpysbqpjwzirsfknnyseiujadovngogvptphuyzkrwgjqwdhtvgxnmxuheofplizpxijfytfabx"));
        System.out.println(question5.longestPalindrome4("jrjnbctoqgzimtoklkxcknwmhiztomaofwwzjnhrijwkgmwwuazcowskjhitejnvtblqyepxispasrgvgzqlvrmvhxusiqqzzibcyhpnruhrgbzsmlsuacwptmzxuewnjzmwxbdzqyvsjzxiecsnkdibudtvthzlizralpaowsbakzconeuwwpsqynaxqmgngzpovauxsqgypinywwtmekzhhlzaeatbzryreuttgwfqmmpeywtvpssznkwhzuqewuqtfuflttjcxrhwexvtxjihunpywerkktbvlsyomkxuwrqqmbmzjbfytdddnkasmdyukawrzrnhdmaefzltddipcrhuchvdcoegamlfifzistnplqabtazunlelslicrkuuhosoyduhootlwsbtxautewkvnvlbtixkmxhngidxecehslqjpcdrtlqswmyghmwlttjecvbueswsixoxmymcepbmuwtzanmvujmalyghzkvtoxynyusbpzpolaplsgrunpfgdbbtvtkahqmmlbxzcfznvhxsiytlsxmmtqiudyjlnbkzvtbqdsknsrknsykqzucevgmmcoanilsyyklpbxqosoquolvytefhvozwtwcrmbnyijbammlzrgalrymyfpysbqpjwzirsfknnyseiujadovngogvptphuyzkrwgjqwdhtvgxnmxuheofplizpxijfytfabx"));
        System.out.println(question5.longestPalindrome5("jrjnbctoqgzimtoklkxcknwmhiztomaofwwzjnhrijwkgmwwuazcowskjhitejnvtblqyepxispasrgvgzqlvrmvhxusiqqzzibcyhpnruhrgbzsmlsuacwptmzxuewnjzmwxbdzqyvsjzxiecsnkdibudtvthzlizralpaowsbakzconeuwwpsqynaxqmgngzpovauxsqgypinywwtmekzhhlzaeatbzryreuttgwfqmmpeywtvpssznkwhzuqewuqtfuflttjcxrhwexvtxjihunpywerkktbvlsyomkxuwrqqmbmzjbfytdddnkasmdyukawrzrnhdmaefzltddipcrhuchvdcoegamlfifzistnplqabtazunlelslicrkuuhosoyduhootlwsbtxautewkvnvlbtixkmxhngidxecehslqjpcdrtlqswmyghmwlttjecvbueswsixoxmymcepbmuwtzanmvujmalyghzkvtoxynyusbpzpolaplsgrunpfgdbbtvtkahqmmlbxzcfznvhxsiytlsxmmtqiudyjlnbkzvtbqdsknsrknsykqzucevgmmcoanilsyyklpbxqosoquolvytefhvozwtwcrmbnyijbammlzrgalrymyfpysbqpjwzirsfknnyseiujadovngogvptphuyzkrwgjqwdhtvgxnmxuheofplizpxijfytfabx"));
    }


    /**
     * 暴力法
     */
    private String longestPalindrome1(String s) {
        if (s == null || s.length() == 0) {
            throw new IllegalArgumentException("");
        }
        char[] arr = s.toCharArray();
        int len = arr.length + 1;
        while (len-- > 2) {
            for (int i = 0; i < arr.length - len + 1; i++) {
                int j = i, k = i + len - 1;
                if (judge(arr, j, k)) {
                    return new String(arr, i, len);
                }
            }
        }
        return new String(arr, 0, 1);
    }

    private boolean judge(char[] arr, int j, int k) {
        while (j < k) {
            if (arr[j] != arr[k]) {
                return false;
            }
            j++;
            k--;
        }
        return true;
    }

    /**
     * 最长公共子字符串方案。
     * <p>
     * arr[i][j] = arr[i-1][j-1] + 1;
     * <p>
     * 使用最长公共子字符串的方法解不开此题，比如：s=“abc534cba”，实际结果是a，但是此方法返回abc。
     * 所以需要在原有逻辑上增加判断 子字符串是否是回文的逻辑。
     */
    private String longestPalindrome2(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        String r = new StringBuilder(s).reverse().toString();
        int[][] arr = new int[s.length()][s.length()];
        int maxIndex = 0;
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(i) == r.charAt(j)) {
                    if (i == 0 || j == 0) {
                        arr[i][j] = 1;
                    } else {
                        arr[i][j] = arr[i - 1][j - 1] + 1;
                    }
                    if (arr[i][j] > maxLen) {
                        //此行代码用于判断公共子字符是否是回文。
                        if (s.charAt(i) == s.charAt(i - arr[i][j] + 1)) {
                            maxLen = arr[i][j];
                            maxIndex = i;
                        }

                    }
                }
            }
        }
        return s.substring(maxIndex - maxLen + 1, maxIndex + 1);
    }

    /**
     * 动态规划。
     * 状态转移方程 p[i,j] = p[i+1,j-1]&&S[i]==S[j];
     * <p>
     * 采用备忘录方法缓存中间结果。
     * <p>
     * 时间复杂度o2.
     */
    private String longestPalindrome3(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        boolean[][] cache = new boolean[s.length()][s.length()];
        int maxLen = 0;
        String res = "";

        //遍历所有长度的子字符串。
        for (int len = 1; len <= s.length(); len++) {
            //遍历该长度下的子字符串。
            for (int start = 0; start < s.length() - len + 1; start++) {
                int end = start + len - 1;
                if (end >= s.length()) {
                    break;
                }
                cache[start][end] = (len == 1 || len == 2 || cache[start + 1][end - 1]) && s.charAt(start) == s.charAt(end);
                if (cache[start][end] && len > maxLen) {
                    maxLen = len;
                    res = s.substring(start, end + 1);
                }
            }
        }
        return res;
    }

    /**
     * 动态规划的另一种解法。
     * <p>
     * 与上一个方法遍历的角度不一样而已。
     */
    private String longestPalindrome4(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        String res = "";
        boolean[][] p = new boolean[s.length()][s.length()];
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                p[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || p[i + 1][j - 1]);
                if (p[i][j] && j - i + 1 > res.length()) {
                    res = s.substring(i, j + 1);
                }
            }
        }
        return res;
    }

    /**
     * 上一版动态规划算法空间优化。
     */
    private String longestPalindrome5(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        String res = "";
        boolean[] p = new boolean[s.length()];
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                p[j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || p[j - 1]);
                if (p[j] && j - i + 1 > res.length()) {
                    res = s.substring(i, j + 1);
                }
            }
        }
        return res;
    }
}