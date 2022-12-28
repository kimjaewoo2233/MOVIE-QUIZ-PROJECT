package com.movie.back.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movie.back.dto.BoxOfficeDTO;
import com.movie.back.dto.MemberDTO;
import com.movie.back.service.BoxOfficeService;
import com.movie.back.service.ImageService;
import com.movie.back.service.MemberService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/mvi")
@RequiredArgsConstructor
public class MainController {

    private final BoxOfficeService boxOfficeService;

    private final MemberService memberService;

    private final JWTUtil jwtUtil;


    @GetMapping(value = "/box")    //박스오피스, 10개 출력
    public ResponseEntity<List<BoxOfficeDTO>> readAll(){
        return ResponseEntity.ok(boxOfficeService.getBoxList());
    }

    @GetMapping(value = "/read")
    public ResponseEntity<BoxOfficeDTO> read(@RequestParam(required = true) String title){

        return ResponseEntity.ok(boxOfficeService.getReadMovie(title));
    }

    @GetMapping(value="/like")
    public ResponseEntity<List<BoxOfficeDTO>> likeOrderBy(HttpServletRequest request){
        try{
            String token = memberService.jwtExtract(request);
            Map<String,Object> values = jwtUtil.validateToken(token);
            MemberDTO memberDTO = memberService.getMember((String)values.get("email"));

            return ResponseEntity.ok(boxOfficeService.likeOrderByAgeGroup(memberDTO.getAgeGroup()));
        }catch (NullPointerException e){   //토큰만료에러남
            return ResponseEntity.ok(boxOfficeService.getListOrderBy());
        }


    }






}
