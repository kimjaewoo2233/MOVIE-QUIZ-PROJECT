package com.movie.back.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BoxOfficeRepositoryTest {


    @Autowired
    private BoxOfficeRepository boxOfficeRepository;

    @Test
    @Transactional
    void test(){
        boxOfficeRepository.getLikeList(PageRequest.of(0,10)).subList(0,10).forEach(System.out::println);
    }


    @Test
    @Transactional
    void test2(){   //퀴즈가 많은 순으로 뽑아내기
        boxOfficeRepository.getQuizeBoxOfficeOrderBy(PageRequest.of(0,10)).forEach(boxOffice -> {
            System.out.println(boxOffice.getTitle());
            System.out.println(boxOffice.getPosterLink());
        });
    }
}