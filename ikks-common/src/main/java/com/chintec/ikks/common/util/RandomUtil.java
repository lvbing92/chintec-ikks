package com.chintec.ikks.common.util;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/8 14:48
 */
public class RandomUtil {

    /**
     * 获取由小写字母，数字，大写字母的n位随机验证码
     *
     * @param n 随机数的位数
     * @return String
     */
    public static String getRandom(Integer n) {
        String[] letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                                        "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String code = "";
        for (int i = 0; i < n; i++) {
            if (Math.random() * 2 > 1) {
                if (Math.random() * 2 > 1) {
                    code = code + letters[(int) Math.floor(Math.random() * 26 + 1)];
                } else {
                    String s = letters[(int) Math.floor(Math.random() * 26 + 1)];
                    code = code + s.toUpperCase();
                }
            } else {
                code = code + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
            }
        }
        return code;
    }

    /**
     * 获取由小写字母，数字，大写字母的n位随机验证码
     *
     * @param n 随机数的位数
     * @return String
     */

    public static String getRandom2(Integer n) {

        String[] letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String code = "";
        for (int i = 0; i < n; i++) {
            code = code + letters[(int) Math.floor(Math.random() * letters.length + 1)];
        }
        return code;
    }

    public static void main(String[] args) {
        new Thread(()->System.out.println("this is myThread")).start();
    }
}
