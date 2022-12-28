package com.movie.back.service;

import com.movie.back.repository.BoxOfficeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class LikeServiceTest {


    @Autowired
    LikeService likeService;

    @Autowired
    BoxOfficeRepository boxOfficeRepository;

    @Test
    void test(){
        boxOfficeRepository.findAll().subList(0,10).forEach(boxOffice -> {
            likeService.saveLike("user",boxOffice.getTitle());
        });
    }
}