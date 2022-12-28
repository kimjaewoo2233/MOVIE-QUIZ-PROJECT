package com.movie.back.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ScrapperServiceTest {

        @Autowired
        ScrapperService service;


        @Test
        void test() throws IOException, KobisScrapper.NotScrappedDateException {
                service.latestBoxOffice().forEach(System.out::println);
        }


}