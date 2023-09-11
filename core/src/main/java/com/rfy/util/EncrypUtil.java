package com.rfy.util;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.algorithms.Type;
import com.amdelamar.jhash.exception.InvalidHashException;

/**
 * Created by Rao on 2023/9/8 14:05
 */
public class EncrypUtil {

    public EncrypUtil() {

    }

    public static String encrypt(String password) {
        char[] chars = password.toCharArray();
        return Hash.password(chars).algorithm(Type.PBKDF2_SHA256).create();
    }

    public static boolean verify(String encryptPassword, String password) {
        char[] chars = password.toCharArray();
        try {
            return Hash.password(chars).algorithm(Type.PBKDF2_SHA256).verify(encryptPassword);
        } catch (InvalidHashException e) {
            return false;
        }
    }
}
