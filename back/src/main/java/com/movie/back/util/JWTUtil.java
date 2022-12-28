package com.movie.back.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JWTUtil {

    @Value("${jwt.secret}")
    private String key;

    public String generateToken(Map<String,Object> valueMap,int days){

        //Header 부분
        Map<String,Object> headers = new HashMap<>();
        headers.put("typ","JWT");   //Token Type
        headers.put("alg","HS256");     //해싱 알고리즘


        //PayLoad
        Map<String,Object> payloads = new HashMap<>();  //실질적인  내용
        payloads.putAll(valueMap);

        int time = (10)*days;        //(60*24) 10분

        String jwtStr = Jwts.builder()
                .setHeader(headers) //header 값
                .setClaims(payloads)    //payloads 내용 바디
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant())) //언제부터
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))    //만료분단위로처리
                .signWith(SignatureAlgorithm.HS256, key.getBytes()) //알고리즘과 키값설정필요
                .compact(); //base64 로 처리가 필요하다.


        return jwtStr;
    }

    public Map<String,Object> validateToken(String token) throws JwtException {
        Map<String,Object> claim = null;

        claim = Jwts.parser()
                .setSigningKey(key.getBytes())  //SetKey
                .parseClaimsJws(token)      //파싱 및 검증, 실패시 에러
                .getBody();

        return claim;
    }
}

