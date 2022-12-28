package com.movie.back.service;

import com.movie.back.entity.BoxOffice;
import com.movie.back.repository.BoxOfficeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoxOfficeServiceTest {

    @Autowired
    BoxOfficeService boxOfficeService;

    @Autowired
    BoxOfficeRepository boxOfficeRepository;
    @Test
    void test() throws IOException, KobisScrapper.NotScrappedDateException {
        boxOfficeService.saveBoxMovie();
    }

    @Transactional
    @Test
        // 옵션 적용
    void test2(){
        System.out.println(boxOfficeRepository.getMovieRead("헤어질 결심"));
    }

    @Test
        // 옵션 적용
    void test3(){
        boxOfficeService.getBoxList().forEach(System.out::println);
    }

    @Test
    @Transactional
    void test4(){
        System.out.println(boxOfficeRepository.getMovieRead("동감"));
    }

    @Test
    @Transactional
    void test5(){
        System.out.println(boxOfficeService.getReadMovie("동감"));
    }

    @Test
    void test6(){
        System.out.println(boxOfficeRepository.getMovieRead("자백").getStillImage().size());
    }

    @Test
    void test7(){
        boxOfficeService.saveSearchBoxOffice(2021,2022);
    }

    @Test
    @Transactional
    void test8(){
        for (BoxOffice boxOffice : boxOfficeRepository.getLikeMovieList(PageRequest.of(0, 10))) {

            System.out.println(boxOffice.getTitle());
            System.out.println(boxOffice.getPosterLink());
            System.out.println(boxOffice.getSynopsis());
            //TODO: 좋아요 개수 처리해야함
        }
    }


    @Test
    @Transactional
    void 라이크테스트(){
            boxOfficeService.likeOrderByAgeGroup("30").forEach(System.out::println);
    }

}