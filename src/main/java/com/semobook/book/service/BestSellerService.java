package com.semobook.book.service;

import com.semobook.book.domain.RecomBestSeller;
import com.semobook.book.domain.RecomSteadySeller;
import com.semobook.book.dto.BookDto;
import com.semobook.book.repository.RecomBestSellerRepository;
import com.semobook.book.repository.RecomSteadySellerRepository;
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
    Map<String, Integer> bestSellerCategoryIndex;
    Map<String, Integer> steadySellerCategoryIndex;
    Map<String, Integer> bestSellerMaxCategoryIndex;
    Map<String, Integer> steadySellerMaxCategoryIndex;
    private final RecomBestSellerRepository recomBestSellerRepository;
    private final RecomSteadySellerRepository recomSteadySellerRepository;

    /**
     * 초기세팅 : 인덱스별 값, 최대인덱스 가져오기
     */
    @PostConstruct
    private void init() {
        bestSellerCategoryIndex = new HashMap<>();
        bestSellerMaxCategoryIndex = new HashMap<>();
        steadySellerCategoryIndex = new HashMap<>();
        steadySellerMaxCategoryIndex = new HashMap<>();
        for (int i = 0; i < SemoConstant.CATEGORY_TYPE.length; i++) {
            bestSellerCategoryIndex.put(SemoConstant.CATEGORY_TYPE[i], 1);
            steadySellerCategoryIndex.put(SemoConstant.CATEGORY_TYPE[i], 1);

            RecomBestSeller bsIdx = recomBestSellerRepository.findById("TOTAL" + SemoConstant.CATEGORY_TYPE[i]).orElse(null);
            if (bsIdx != null) {
                bestSellerMaxCategoryIndex.put(SemoConstant.CATEGORY_TYPE[i], Integer.parseInt(bsIdx.getIsbn()));
            }
            RecomSteadySeller ssIdx = recomSteadySellerRepository.findById("TOTAL" + SemoConstant.CATEGORY_TYPE[i]).orElse(null);
            if (ssIdx != null) {
                steadySellerMaxCategoryIndex.put(SemoConstant.CATEGORY_TYPE[i], Integer.parseInt(ssIdx.getIsbn()));
            }
        }


    }

    public List<BookDto> getBestSellerSteadySellerListMix(String cate, int num){
        List<BookDto> bookList = new ArrayList<>();
        int divNum = num/2 +1;
        bookList.addAll(getBestSellerList(cate,divNum));
        bookList.addAll(getSteadySellerList(cate,divNum));
        return bookList;
    }

    /**
     * 베스트셀러 카테고리별 개수별로 가져오기
     *
     * @author hyejinzz
     * @since 2021-06-20
     **/
    public List<BookDto> getBestSellerList(String cate, int num) {
        List<BookDto> bookList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            bookList.add(getBestSeller(cate));
        }
        return bookList;
    }

    /**
     * 스테디셀러 카테고리별 개수벼롤 가져오기
     *
     * @author hyejinzz
     * @since 2021-07-15
     **/
    public List<BookDto> getSteadySellerList(String cate, int num) {
        List<BookDto> bookList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            bookList.add(getSteadySeller(cate));
        }
        return bookList;
    }


    /**
     * 종류별 1개의 데이터를 가져온다
     *
     * @author hyejinzz
     * @since 2021-06-20
     **/
    public BookDto getBestSeller(String key) {
        int idx = bestSellerCategoryIndex.get(key);
        int maxIdx = bestSellerMaxCategoryIndex.get(key);
        log.info(":: getBestSeller :: test is {} ", key+"_"+idx);
        RecomBestSeller bs = recomBestSellerRepository.findById(key+ "_"+ idx++).orElse(null);
        idx = idx > maxIdx ? 1 : idx;
        bestSellerCategoryIndex.put(key, idx);
        if (bs != null) {
            log.info(":: getBestSeller :: test is {} ", bs.getIsbn());
        }
        return bs == null ?     new BookDto() :
                BookDto.builder()
                        .author(bs.getAuthor())
                        .bookName(bs.getBookName())
                        .img(bs.getImg())
                        .category(bs.getCategory())
                        .isbn(bs.getIsbn())
                        .publisher(bs.getPublisher())
                        .build();
    }

    public BookDto getSteadySeller(String key) {

        int idx = steadySellerCategoryIndex.get(key);
        int maxIdx = steadySellerMaxCategoryIndex.get(key);
        log.info(":: getSteadySeller :: test is {} ", key+"_"+idx);

        RecomSteadySeller ss = recomSteadySellerRepository.findById(key +"_"+ idx++).orElse(null);

        idx = idx > maxIdx ? 1 : idx;
        steadySellerCategoryIndex.put(key, idx);
        if (ss != null) {
            log.info(":: getSteadySeller :: test is {} ", ss.getIsbn());
        }
        return ss == null ?
                new BookDto() :
                BookDto.builder()
                        .author(ss.getAuthor())
                        .bookName(ss.getBookName())
                        .img(ss.getImg())
                        .category(ss.getCategory())
                        .isbn(ss.getIsbn())
                        .publisher(ss.getPublisher())
                        .build();
    }

}
