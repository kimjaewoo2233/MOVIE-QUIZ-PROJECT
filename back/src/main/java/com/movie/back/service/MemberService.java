package com.movie.back.service;


import com.movie.back.dto.MemberDTO;
import com.movie.back.dto.MovieCommentsDTO;
import com.movie.back.dto.myData.MyMovieData;
import com.movie.back.dto.myData.MyQuiz;
import com.movie.back.dto.myData.ResponseMyDataDTO;
import com.movie.back.entity.Member;
import com.movie.back.repository.MemberRepository;
import com.movie.back.repository.MovieCommentRepository;
import com.movie.back.repository.QuizRepository;
import com.movie.back.security.exception.AccessTokenException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final QuizRepository quizRepository;

    private final MovieCommentRepository commentRepository;


    public MemberDTO memberRegister(MemberDTO memberDTO){
            memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        System.out.println(MemberDTO.toEntity(memberDTO));
            return MemberDTO.toDTO(memberRepository.save(MemberDTO.toEntity(memberDTO)));
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

    public MemberDTO getMember(String email){
        Member member = memberRepository.getMemberInfo(email).orElse(null);
        if(member != null){
            return MemberDTO.toDTO(member);
        }else{
            return null;
        }

    }

    @Transactional
    public ResponseMyDataDTO myPageData(String email){
            List<ResponseMyDataDTO> list = new ArrayList<>();
            ResponseMyDataDTO responseMyDataDTO = ResponseMyDataDTO.builder().build();
            List<MyMovieData> movieDataList = new ArrayList<>();
            List<MyQuiz> quizList = new ArrayList<>();
            List<MovieCommentsDTO> commentsDTOS = new ArrayList<>();



            Member member = memberRepository.getMemberInfo(email).orElseThrow(RuntimeException::new);

            responseMyDataDTO.setEmail(member.getEmail());
            responseMyDataDTO.setBirth(member.getBirth());
            responseMyDataDTO.setGender(member.getGender());
            responseMyDataDTO.setRoleSet(member.getRoleSet().stream()
                    .map(memberRole -> memberRole.toString()).collect(Collectors.toUnmodifiableSet()));

            member.getMovieSet().forEach(memberMovie -> {
                movieDataList
                                .add(MyMovieData.builder()
                                .posterLink(memberMovie.getBoxOfficeId().getPosterLink())
                                        .title(memberMovie.getTitle())
                                .build());
            });
            responseMyDataDTO.setMyMovieDataList(movieDataList);


            quizRepository.getQuizByEmail(member.getEmail()).forEach(quiz -> {

                                  quizList.add(MyQuiz.builder()
                                            .id(quiz.getId())
                                            .movieTitle(quiz.getMovieTitle())
                                            .quizName(quiz.getTitle())
                                                  .quizItems(quiz.getQuizItems().stream()
                                                          .map(quizItems -> quizItems.getItemTitle())
                                                          .collect(Collectors.toUnmodifiableSet()))
                                    .build());
            });
            responseMyDataDTO.setQuizDTOList(quizList);

            commentRepository.findAllWithRatingByUser(member
                    .getEmail()).forEach(movieComments -> {
                          commentsDTOS.add(MovieCommentsDTO.builder()
                                                .id(movieComments.getId())
                                                .content(movieComments.getContent())
                                                .blind(movieComments.isBlind())
                                                .spoiler(movieComments.isSpoiler())
                                                .rating(movieComments.getRating().getRating().toString())
                                                .createdAt(movieComments.getCreatedAt())
                                        .build());
            });
            responseMyDataDTO.setCommentsDTOList(commentsDTOS);


        return responseMyDataDTO;
    }
}