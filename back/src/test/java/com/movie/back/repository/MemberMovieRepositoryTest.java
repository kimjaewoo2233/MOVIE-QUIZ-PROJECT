package com.movie.back.repository;

import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.Member;
import com.movie.back.entity.MemberMovie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemberMovieRepositoryTest {

    @Autowired
    MemberMovieRepository memberMovieRepository;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;
    @Test
    @Transactional
    void test(){
    }

    @Test
    @Rollback(value = false)
    @Transactional
    void test2(){
        //해당 계정에 찜한 영화들이 나오고 중복이 허용된다. 중복된 값까지 지워야한다.
        memberMovieRepository.deleteMyMovie("데시벨","user");
//        em.flush();
//        MemberMovie movieMovie = memberMovieRepository.findById(1L).get();
//
//        movieMovie.changeMember(null);

    }

    @Test
    void test3(){
        System.out.println( memberMovieRepository.existysMyMovie("데시벨","user"));
        System.out.println( memberMovieRepository.existysMyMovie("아바타","user"));
    }

    @Test
    void 찜한영화저장하기(){
        memberMovieRepository.save(MemberMovie.builder()
                        .title("데시벨")   //영화제목
                        .email("user")  //해당 이메일
                .build());
    }

}