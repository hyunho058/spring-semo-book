package tools;

import com.semobook.common.SemoConstant;

import java.util.Arrays;
import java.util.List;

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
     * String을 | 구본자 사용해서 List로 변환
     *
     * @param basicString List로 변환할 String
     * @author hyejinzz
     * @since 2021-07-04
     **/
    public static List<String> StringConvToListUsingBackSlash(String basicString) {
        StringBuilder sb = new StringBuilder();
        return Arrays.asList(basicString.split(SemoConstant.BACKSLASH_VARTICAL_BAR));
    }
}
