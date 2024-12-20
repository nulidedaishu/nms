package com.itany.nmms.util;

public class ParameterUtil {

    public static boolean isNull(String s) {
        return null == s || "".equals(s);
    }

    public static String nextValue(String s){
        int i=Integer.parseInt(s)+1;
        s="00000"+i;
        return s.substring(s.length()-6);
    }

    public static String escapeString(String s) {
        if (!ParameterUtil.isNull(s)) {
            char[] cs = s.toCharArray();
            StringBuffer buffer = new StringBuffer();
            for (char c : cs) {
                buffer.append("/" + c);
            }
            return buffer.toString();
        }
        return null;
    }
}
