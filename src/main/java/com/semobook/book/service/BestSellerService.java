package com.semobook.book.service;

import com.semobook.book.domain.RecomBestSeller;
import com.semobook.book.repository.RecomBestSellerRepository;
import com.semobook.common.SemoConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class BestSellerService {
    Map<String, Integer> categoryIndex;
    private final RecomBestSellerRepository recomBestSellerRepository;

    /**
     * 초기세팅 : 인덱스별 값
     */
    @PostConstruct
    private void init() {
        categoryIndex = new HashMap<>();
        for (int i = 0; i < SemoConstant.CATEGORY_TYPE.length; i++) {
            categoryIndex.put(SemoConstant.CATEGORY_TYPE[i], 1);
        }

    }

    /**
     * 카테고리별 개수별로 가져오기
     *
     * @author hyejinzz
     * @since 2021-06-20
     **/
    public List<RecomBestSeller> getBestSellerList(String cate, int num) {
        int index = categoryIndex.get(cate);
        List<RecomBestSeller> bookList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            bookList.add(getBestSeller(cate));
        }
        return bookList;
    }


    /**
     * 종류별 1개의 데이터를 가져온다
     *
     * @author hyejinzz
     * @since 2021-06-20
     **/
    public RecomBestSeller getBestSeller(String key) {
        int idx = categoryIndex.get(key);
        RecomBestSeller bs = recomBestSellerRepository.findById(key+idx++).orElse(null);
        idx = idx > 20 ? 1 : idx;
        categoryIndex.put(key, idx);
        if (bs != null) {
            log.info(":: getFromRedis :: test is {} ", bs.getIsbn());
        }
        return bs == null ? new RecomBestSeller() : bs;

    }

}
