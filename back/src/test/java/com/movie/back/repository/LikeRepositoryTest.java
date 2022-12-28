package com.movie.back.repository;

import com.movie.back.dto.BoxOfficeDTO;
import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.LikeGood;
import com.movie.back.entity.Member;
import com.movie.back.service.BoxOfficeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@SpringBootTest
class LikeRepositoryTest {

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    QuizRepository quizRepository;


    @Autowired
    BoxOfficeService boxOfficeService;
    @Test
    void saveTest(){
            likeRepository.save(LikeGood.builder()
                            .member(Member.builder().email("user").build())
                            .boxOffice(BoxOffice.builder().title("헤어질 결심").build())
                    .build());
    }

    @Test
    void exitTest(){//성공
        System.out.println(likeRepository.exists(BoxOffice.builder().title("헤어질 결심").build()
                ,Member.builder().email("user").build()));

    }
    @Test
    void exitTest2(){
        System.out.println(likeRepository.likeElement(
                BoxOffice.builder().title("헤어질 결심").build(), Member.builder().email("user").build())
        );

    }

    @Test
    @Transactional
    void 라이크오더바이(){
        Set<BoxOfficeDTO> set = new HashSet<>();
        likeRepository.likeGoodOrderBy(Integer.toString(30)).forEach(likeGood -> {
                    set.add(BoxOfficeDTO.builder()
                                    .title(likeGood.getBoxOffice().getTitle())
                                    .postLink(likeGood.getBoxOffice().getPosterLink())
                            .build());
        });

        set.forEach(System.out::println);
        System.out.println(set.size());
    }


    @Test
    @Transactional
    void test13(){
        boxOfficeService.likeOrderByAgeGroup("30").forEach(System.out::println);
    }
}