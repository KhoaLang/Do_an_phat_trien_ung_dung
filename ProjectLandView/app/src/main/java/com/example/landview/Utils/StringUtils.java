package com.example.landview.Utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {
    public String vietnameseConvertToEngAndLowerCase(String s){
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").toLowerCase();
    }
}
