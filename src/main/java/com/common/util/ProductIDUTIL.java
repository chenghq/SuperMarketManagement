package com.common.util;

import java.util.ArrayList;


public class ProductIDUTIL {
	/**
     * 获取UUID
     * 
     * @return
     */
    public static String getID() {
        String s1 = byteArr262Str(System.currentTimeMillis());
        return randString(4) + s1;
    }
    
    private static ArrayList<Integer> base62(long id) {
        ArrayList<Integer> value = new ArrayList<Integer>();
        long id1 = id;
        while (id1 > 0) {
            Long remainder = id1 % 62;
            value.add(remainder.intValue());
            id1 = id1 / 62;
        }
        int iLen = value.size();
        for(int i = 0;i + iLen < 6;i++){
            value.add(0);
        }
        return value;
    }
    
    private static String byteArr262Str(long id) {
        ArrayList<Integer> arrB = base62(id);
        int iLen = arrB.size();
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = iLen - 1; i >= 0; i--) {
            Integer intTmp = arrB.get(i);
            if (intTmp < 26) {//a~z
                intTmp = intTmp + 97;
                sb.append(Character.toChars(intTmp)[0]);
            }else if(intTmp >= 26 && intTmp < 52){//A~Z
                intTmp = intTmp + 39;
                sb.append(Character.toChars(intTmp)[0]);
            }else{//0~9
                intTmp = intTmp - 52;
                sb.append(intTmp + "");
            }
        }
        return sb.toString();
    }

    /**
     * 随机字符串
     * 
     * @param n
     * @return
     */
    public static String randString(int n) {
        String[] ss = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3",
                "4", "5", "6", "7", "8", "9" };
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            Long num = (long) Math.floor(Math.random() * 62);
            sb.append(ss[num.intValue()]);
        }
        return sb.toString();
    }

    /**
     * 随机数字
     * 
     * @param n
     * @return
     */
    public static String randNum(int n) {
        String[] ss = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            Long num = (long) Math.floor(Math.random() * 10);
            sb.append(ss[num.intValue()]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getID());
    }
}
