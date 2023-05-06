package com.example.itemserver.common;


import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtils {
    public static boolean matchByPattern(String value, String regex){
        if(nullOrEmpty(regex) || nullOrEmpty(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
    public static boolean notNullOrEmpty(Collection objects) {
        return !nullOrEmpty(objects);
    }

    public static boolean nullOrEmpty(Collection objects) {
        return objects == null || objects.isEmpty();
    }
    public static boolean nullOrEmpty(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNullOrEmpty(MultipartFile file) {
        if(!file.isEmpty()){
            return true;
        }else {
            return false;
        }
    }


}
