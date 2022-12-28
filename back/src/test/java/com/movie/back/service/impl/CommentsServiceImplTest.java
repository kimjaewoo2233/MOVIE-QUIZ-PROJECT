package com.movie.back.service.impl;

import com.movie.back.dto.MovieCommentsDTO;
import com.movie.back.entity.MovieComments;
import com.movie.back.repository.MovieCommentRepository;
import com.movie.back.service.CommentsService;
import com.movie.back.service.MovieMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CommentsServiceImplTest {

    @Autowired
    CommentsService commentsService;

    @Autowired
    MovieCommentRepository commentRepository;


    @Test
    void test(){    ///TODO: 코드로 옮겨야함
            MovieComments movieComments =commentRepository.findById(13L).get();
        movieComments.addBlindNumber();
        commentRepository.save(movieComments);
    }

    @Test
    void test2(){
        commentsService.getDTOList(0,"동감").getDtoList().forEach(System.out::println);
    }
}