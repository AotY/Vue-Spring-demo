package com.xjtu.util;

import com.xjtu.common.Const;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by LeonTao on 2017/9/28.
 */
public class MD5Util {


    public static String MD5Encode(String origin, String charset) {
        String encodeString = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            // 没有指定编码
            if (charset == null || StringUtils.isBlank(charset)) {
                encodeString = new String(messageDigest.digest(origin.getBytes()));
            } else {
//                System.out.println(messageDigest.digest(origin.getBytes(charset)).length);
                byte[] encodeByte = messageDigest.digest(origin.getBytes(charset));
                encodeString = byteArray2HexString(encodeByte);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodeString.toUpperCase();
    }

    public static String MD5EncodeUTF8(String origin) {
        String orgin = origin + PropertiesUtil.getProperty(Const.PASSWORD_SALT);
        return MD5Encode(origin, "UTF-8");
    }

    /**
     * 将byte数组转换为十六进制字符串
     *
     * @param encodeByte
     * @return
     */
    private static String byteArray2HexString(byte[] encodeByte) {

        // String buffers are safe for use by multiple threads.
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodeByte.length; i++) {
            hexString.append(hex2String(encodeByte[i]));
        }
        return hexString.toString();
    }


    /**
     * @param b
     * @return
     */
    private static String hex2String(byte b) {
        int n = b;
        // 如果小于0，取补
        if (n < 0) {
            n += 256;
        }
        int n1 = n / 16;
        int n2 = n % 16;
        return hexDigits[n1] + hexDigits[n2];
    }


    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F"
    };

    public static void main(String[] args) {
        System.out.println(MD5EncodeUTF8("1234"));
    }

}




