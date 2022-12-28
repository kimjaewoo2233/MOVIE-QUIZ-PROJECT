package com.movie.back.service;

import com.movie.back.repository.QuizItemsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class QuizServiceTest {

    @Autowired
    QuizService quizService;

    @Autowired
    QuizItemsRepository quizItemsRepository;


    @Test
    @Transactional
    void 서비스퀴즈테스트(){

            quizService.getQuiz("데시벨").forEach(System.out::println);

            //TODO: 영화제목을 넣으면 퀴즈가 나오는 것까지 완료함 이제 퀴즈 생성, 퀴즈 권한체크(이거고민중)
    }

    @Test
    @Transactional
    void 퀴즈문항테스트(){

    }

    @Test
    void 지우기(){
        quizItemsRepository.deleteAll();
    }
}