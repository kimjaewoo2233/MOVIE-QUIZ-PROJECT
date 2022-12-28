package com.movie.back.service;

import com.movie.back.data.cdata.BoxOfficeData;
import com.movie.back.data.cdata.MovieCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class KobisScrapperTest {


    @Autowired
    ScrapperService scrapperService;
    @Test
    @DisplayName("크롤링테스트")
    void test1() throws IOException, KobisScrapper.NotScrappedDateException {
        System.out.println(LocalDate.now().minusDays(7L));
        KobisScrapper kobisScrapper =
                new KobisScrapper(LocalDate.now().minusDays(7L), LocalDate.now().minusDays(2L));
        for(BoxOfficeData boxOfficeData : kobisScrapper.getBoxOfficesByDate(LocalDate.now().minusDays(2L))){
            String[] str = kobisScrapper.getImageUrlsByCode(boxOfficeData.getCode(), KobisScrapper.ImageType.POSTER,true);
            for(String st : str){
                System.out.println(st);
            }
        }

    }

    @Test
    @DisplayName("크롤링테스트")
    void test2() throws IOException, KobisScrapper.NotScrappedDateException {
        System.out.println(LocalDate.now().minusDays(7L));
        KobisScrapper kobisScrapper =
                new KobisScrapper(LocalDate.now().minusDays(7L), LocalDate.now().minusDays(2L));
        for(BoxOfficeData boxOfficeData : kobisScrapper.getBoxOfficesByDate(LocalDate.now().minusDays(2L))){
            //해당 날짜에 박스오피스 데이터들 rank/title/code
            System.out.println(" 순위 ==="+boxOfficeData.getRank());
            System.out.println(" 제목 ==="+boxOfficeData.getTitle());
            String[] str = kobisScrapper.getImageUrlsByCode(boxOfficeData.getCode(), KobisScrapper.ImageType.POSTER,true);
            //스틸컷 enum 변경해서 받아오면됨 포스터도 여러개ㅔ 스틸컷은 더 많음
            System.out.println(kobisScrapper.getSynopsisByCode(boxOfficeData.getCode()));
            for(String st : str){
                System.out.println(st);
            }
        }

    }
    @Test
    @DisplayName("크롤링테스트2")
    void test3() throws IOException, KobisScrapper.NotScrappedDateException {
        System.out.println(LocalDate.now().minusDays(7L));
        KobisScrapper kobisScrapper =
                new KobisScrapper(LocalDate.now().minusDays(7L), LocalDate.now().minusDays(2L));
        for(BoxOfficeData boxOfficeData : kobisScrapper.getBoxOfficesByDate(LocalDate.now().minusDays(2L))){
            //해당 날짜에 박스오피스 데이터들 rank/title/code
            System.out.println(" 순위 ==="+boxOfficeData.getRank());
            System.out.println(" 제목 ==="+boxOfficeData.getTitle());
            System.out.println(" 개봉일 ==="+boxOfficeData.getDate());
            System.out.println(" 포스터 ==="+KobisScrapper.getMainPosterByCode(boxOfficeData.getCode()));
            KobisScrapper.getActorList(boxOfficeData.getCode()).stream()
                    .filter(actor -> actor.getType().equals("주연"))
                    .collect(Collectors.toList()).forEach(actor -> System.out.println(actor.getCharacterName()+actor.getActorName()));
            String[] str = kobisScrapper.getImageUrlsByCode(boxOfficeData.getCode(), KobisScrapper.ImageType.STILL_CUT,true);
            //스틸컷 enum 변경해서 받아오면됨 포스터도 여러개ㅔ 스틸컷은 더 많음
            System.out.println(kobisScrapper.getSynopsisByCode(boxOfficeData.getCode()));
            for(String st : str){
                System.out.println(st);
            }
        }

    }
    @Test
    @DisplayName("크롤링테스트3")
    void test4() throws IOException {


        IntStream.rangeClosed(1,34).forEach(value -> {
         MovieCode[] movieCodes = new MovieCode[0];
            try {
                movieCodes = Optional.of(KobisScrapper.searchUserMovCdList(2021, 2022, value)).orElseThrow(NullPointerException::new);
            } catch (NullPointerException nullPointerException) {
                System.out.println("끝");
            }
            for (MovieCode movieCode : movieCodes) {
                if (movieCode != null) {
                    try {
                        KobisScrapper.getActorList(movieCode.getCode()).forEach(System.out::println);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        System.out.println(KobisScrapper.getMainPosterByCode(movieCode.getCode()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        System.out.println(Optional.of(KobisScrapper.getSynopsisByCode(movieCode.getCode())).orElse("없음"));
                    } catch (Exception e) {
                        System.out.println("여기서 에러");
                    }
                    try {
                        Arrays.stream(KobisScrapper.getImageUrlsByCode(movieCode.getCode(), KobisScrapper.ImageType.STILL_CUT, true)).forEach(System.out::println);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("반복문에 마지막 번호" + value);
                }
            }

        });
    }

    @Test
    void test5() throws KobisScrapper.NotScrappedDateException, IOException {
        scrapperService.latestBoxOffice().forEach(System.out::println);
    }
}