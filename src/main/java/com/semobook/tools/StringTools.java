package com.semobook.tools;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class StringTools {
    /**
     * 리스트를 구분자를 넣은 String으로 변환
     *
     * @param list      String으로 변환할 List
     * @param separator 구분자
     * @author hyejinzz
     * @since 2021-07-04
     **/
    public static String listConvToString(List<String> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            sb.append(separator);
        }
        sb.deleteCharAt(sb.lastIndexOf(separator));
        return sb.toString();
    }


    /**
     * String을 | 구분자 사용해서 List로 변환
     *
     * @param basicString List로 변환할 String
     * @param separator 구분자
     * @author hyejinzz
     * @since 2021-07-04
     **/
    public static List<String> stringConvToList(String basicString, String separator) {
        return Arrays.asList(basicString.split(separator));
    }
}
