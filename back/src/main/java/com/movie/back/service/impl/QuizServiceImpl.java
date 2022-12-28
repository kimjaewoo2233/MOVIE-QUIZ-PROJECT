package com.movie.back.service.impl;


import com.movie.back.dto.MemberRole;
import com.movie.back.dto.QuizDTO;
import com.movie.back.dto.QuizItems;
import com.movie.back.entity.Member;
import com.movie.back.entity.Quiz;
import com.movie.back.repository.BoxOfficeRepository;
import com.movie.back.repository.MemberRepository;
import com.movie.back.repository.QuizRepository;
import com.movie.back.security.exception.AccessTokenException;
import com.movie.back.service.MemberService;
import com.movie.back.service.QuizService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final BoxOfficeRepository boxOfficeRepository;
    private final QuizRepository quizRepository;

    private final MemberRepository memberRepository;

    private final JWTUtil jwtUtil;



    public List<QuizDTO> getQuiz(String title){
        List<QuizDTO> quizDTOList = new ArrayList<>();
        quizRepository.getQuizByMovieTitle(title).forEach(quiz -> {

                    quizDTOList.add(QuizDTO.builder()
                                    .id(quiz.getId())
                                    .quizTitle(quiz.getTitle())
                                    .movieTitle(quiz.getMovieTitle())
                                    .quizItems(quiz.getQuizItems().stream().map(quizItems -> QuizItems.builder()
                                            .key(quizItems.getKeyNumber())
                                            .correct(quizItems.isCorrect())
                                            .item(quizItems.getItemTitle())
                                            .build()).collect(Collectors.toList()))
                            .build());
        });
        return quizDTOList;
    }

    public List<QuizItems> getItems(String id){
        List<QuizItems> itemsList = new ArrayList<>();
        quizRepository.quizDetailById(Long.parseLong(id)).orElseThrow(RuntimeException::new)
                    .getQuizItems().forEach(quizItem ->{
                            itemsList.add(QuizItems.builder()
                                            .item(quizItem.getItemTitle())
                                            .key(quizItem.getKeyNumber())
                                    .build());
                    });
        return itemsList;
    }

    @Override
    public boolean getCheckQuiz(String item) {


        return false;
    }

    @Override
    public boolean getAddRoleQuiz(HttpServletRequest request) { //권한이 없으면 추가하고 있으면 그냥 빠져나옴
        String token = jwtExtract(request);
        Map<String,Object> jwt = jwtUtil.validateToken(token);

        var member = memberRepository.getMemberInfo((String)jwt.get("email"));
        Member result;
        member.ifPresent(memberInfo -> {
            memberInfo.getRoleSet().forEach(memberRole -> {
                        if (memberRole != MemberRole.ADMIN) {
                            memberInfo.addRole(MemberRole.ADMIN);
                            memberRepository.save(memberInfo);
                        } else {
                            return;
                        }
                    }
                    );
        });

        return true;
    }

    @Override
    @Transactional
    public boolean deleteQuizFeature(String id,String email) {
        Quiz quiz = quizRepository.findById(Long.parseLong(id)).orElseThrow(RuntimeException::new);
        if(quiz.getEmail().equals(email)){
            quizRepository.deleteById(Long.parseLong(id));
        }else{
            return false;
        }

        return true;
    }

    public void saveQuiz(String movieTitle,String email,String quizTitle ,List<QuizItems> quizItems,String correct){
            Quiz quiz =Quiz.builder()
                    .movieTitle(movieTitle)
                    .email(email)
                    .title(quizTitle)
                    .build();

        quizItems.forEach(quizItem -> {
                if(quizItem.getKey().equals(correct)){
                    quiz.addQuizItem(quizItem.getItem(),quizItem.getKey(),true);
                }else{
                    quiz.addQuizItem(quizItem.getItem(),quizItem.getKey(),false);
                }
        });
        quizRepository.save(quiz);
    }
    public String jwtExtract(HttpServletRequest request){
        String headerStr = request.getHeader("Authorization");

        String tokenType = headerStr.substring(0,6);
        String tokenStr = headerStr.substring(7);

        if(tokenType.equalsIgnoreCase("Bearer") == false){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        return tokenStr;
    }


}
