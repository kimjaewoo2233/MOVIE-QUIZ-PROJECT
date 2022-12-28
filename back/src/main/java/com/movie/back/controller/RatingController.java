package com.movie.back.controller;


import com.movie.back.service.BoxOfficeService;
import com.movie.back.service.MemberService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;
    private final BoxOfficeService boxOfficeService;

    @PostMapping("/rating/save/{title}/{rating}")
    public ResponseEntity<Boolean> get(@PathVariable("title") String title
            , @PathVariable("rating") String rating, HttpServletRequest request){
               String token = memberService.jwtExtract(request);
               Map<String,Object> jwt = jwtUtil.validateToken(token);
                boxOfficeService.setMovieRating(title
                        ,(String)jwt.get("email")
                        ,Double.parseDouble(rating));

               return ResponseEntity.ok(true);
    }
}
