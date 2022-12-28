package com.movie.back.controller;


import com.movie.back.dto.QuizDTO;
import com.movie.back.dto.QuizItems;
import com.movie.back.service.MemberService;
import com.movie.back.service.QuizService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/quiz/save")   //
    public ResponseEntity<Boolean> saveQuiz(HttpServletRequest request,
                                           @RequestBody QuizDTO quizDTO){
             String token = memberService.jwtExtract(request);
             Map<String,Object> map = jwtUtil.validateToken(token);
             quizService.saveQuiz(quizDTO.getMovieTitle(),(String) map.get("email"), quizDTO.getQuizTitle(),quizDTO.getQuizItems(),quizDTO.getCorrect());

           return ResponseEntity.ok(true);
    }//saveQuiz(String movieTitle,String email,String quizTitle)

    @GetMapping("/mvi/problem")
    public ResponseEntity<List<QuizDTO>> getQuiz(@RequestParam String title){
            return ResponseEntity.ok(quizService.getQuiz(title));
    }

    @GetMapping("/items")   //quiz 는 Context 방문하기에 한번더 쿼리가 일어남 그거 방지위함
    public ResponseEntity<List<QuizItems>> getQuizItems(@RequestParam String id){

            return ResponseEntity.ok(quizService.getItems(id));
    }

    @GetMapping("/add/role")        //답맞추기
    public ResponseEntity<Boolean> checkSolution(@RequestParam(required = true) Boolean bool
                                                 ,HttpServletRequest request){
       if(bool){    //권한있는지 확인하고 있으면 통과 없으면 추가
           return ResponseEntity.ok(quizService.getAddRoleQuiz(request));

       }else{
           //틀린경우
           return ResponseEntity.ok(false);
       }

    }
    @DeleteMapping("/quiz/delete")
    public ResponseEntity<Boolean> deleteQuiz(@RequestParam(required = true) String id,HttpServletRequest request){
            String token = memberService.jwtExtract(request);
            Map<String,Object> map = jwtUtil.validateToken(token);

            return ResponseEntity.ok(quizService.deleteQuizFeature(id,(String)map.get("email")));
    }
}
