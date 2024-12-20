package com.itany.nmms.util;

import com.itany.nmms.exception.MD5ErrorException;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String md5(String password){
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            byte[] b=md.digest(password.getBytes());
            BASE64Encoder encoder=new BASE64Encoder();
            return encoder.encode(b);
        } catch (NoSuchAlgorithmException e) {
            throw new MD5ErrorException("MD5加密出错");
        }
    }
}
