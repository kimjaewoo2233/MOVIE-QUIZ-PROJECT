package com.movie.back.repository;

import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.Member;
import com.movie.back.entity.Quiz;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class QuizRepositoryTest {


    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private BoxOfficeRepository boxOfficeRepository;

    @Autowired
    private QuizItemsRepository quizItemsRepository;


    @Test
    void 퀴즈생성하기(){
        BoxOffice boxOffice = boxOfficeRepository.findById("데시벨").get();
        Member member = Member.builder().email("user22").build();

        IntStream.rangeClosed(1,3).forEach(i -> {
                quizRepository.save(Quiz.builder()
                                .title("Quiz Title..."+i)
                                .member(member)
                                .boxOffice(boxOffice)
                        .build());
        });
    }

    @Test
    @Transactional
    void 킈즈꺼내오기(){
//
        boxOfficeRepository.getQuizeBoxOffice("데시벨").getQuizList().forEach(quiz -> {
                System.out.println("문항 -> "+quiz.getTitle());
                quiz.getQuizItems().forEach(System.out::println);
        });
    }

    @Test
    void 삭제(){
        quizRepository.deleteAll();
    }

    @Test
    @Transactional
    void 문제확인(){
        String correct = null;

        boxOfficeRepository.getQuizeBoxOffice("데시벨").getQuizList().forEach(quiz -> {
            System.out.println("퀴즈 제목 =====> "+quiz.getTitle());
            quiz.getQuizItems().forEach(quizItem -> {
                System.out.print(quizItem.getKeyNumber()+"번 문항");
                System.out.println(quizItem.getItemTitle());

            });


        });

        System.out.println("여기서 퀴즈가 나옴-------");
        quizRepository.quizDetail("이것이 퀴즈입니다").get().getQuizItems().forEach(System.out::println);
    }

    @Test
    @Transactional
    void 쿼리줄이는퀴즈테스트(){
        quizRepository.getQuizByMovieTitle("데시벨").forEach(System.out::println);
    }

}