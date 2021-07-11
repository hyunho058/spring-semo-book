package com.semobook.common;

import java.util.HashMap;
import java.util.Map;

public class SemoConstant {
    public static final String[] CATEGORY_TYPE = {"A_", "B_", "C_", "D_", "E_", "F_", "G_", "H_", "I_",
            "J_", "K_", "L_", "M_", "N_", "Q_", "R_", "S_", "T_", "U_",
            "V_", "a_", "b_", "c_", "d_", "e_", "f_", "g_"};

    public static final Map<String, String> CATEGORY_TYPE_MAP = new HashMap<String, String>() {
        {
            put("A_", "종합");
            put("B_", "소설");
            put("C_", "에세이");
            put("D_", "국내소설");
            put("E_", "국외소설");
            put("F_", "시");
            put("G_", "어린이");
            put("H_", "가정생활");
            put("I_", "인문");
            put("J_", "정치사회");
            put("K_", "경제경영");
            put("L_", "건강");
            put("M_", "교양과학");
            put("N_", "외국어");
            put("Q_", "예술");
            put("R_", "취미/스포츠");
            put("S_", "TOEIC/TOEFL");
            put("T_", "유아");
            put("U_", "종교");
            put("V_", "아동만화");
            put("a_", "요리/와인");
            put("b_", "역사/문화");
            put("c_", "자기계발");
            put("d_", "여행");
            put("e_", "기술/컴퓨터");
            put("f_", "만화");
            put("g_", "청소년");

        }
    };

    public static final String REDIS_KEY_BEST_SELLER = "RECOM_BEST_SELLER:";

    public static final int FIRST_PRIORITY_RATIO = 5;
    public static final int SECOND_PRIORITY_RATIO = 3;
    public static final int THRID_PRIORITY_RATIO = 2;
    public static final int FIRTH_PRIORITY_RATIO = 1;
    public static final int FIFTH_PRIORITY_RATIO = 1;

    public static final String VARTICAL_BAR = "|";
    public static final String BACKSLASH_VARTICAL_BAR = "\\|";
    public static final String COLON = ":";

    //
    public static final int CHECK_USER_REVIEW_CNT = 5;

    public static final String OPEN_API_KAKAO_BOOK = "https://dapi.kakao.com";
    public static final String KAKAO_AK_BOOK_SEARCH = "KakaoAK a85301089026f3d76b61ac72f59b1d91";

}
