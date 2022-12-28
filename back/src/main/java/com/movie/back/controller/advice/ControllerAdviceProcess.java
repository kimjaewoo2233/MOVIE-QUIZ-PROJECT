package com.movie.back.controller.advice;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerAdviceProcess {


        @ExceptionHandler(NullPointerException.class)
        public boolean errorHandler(Exception e){
            return false;
        }

       // @ResponseStatus(HttpStatus.NOT_FOUND)
        @ResponseStatus(HttpStatus.OK)
        @ExceptionHandler(NoHandlerFoundException.class)
        public ResponseEntity<String> handle404(NoHandlerFoundException exception) {
            return ResponseEntity.ok("<h3>404</h3>");    //404에러시 404 값이 넘어가게 바꿈
        }

        @ExceptionHandler(ExpiredJwtException.class)
        public ResponseEntity<String> expirecdToken(){
            return ResponseEntity.status(403).body("Expired Token");
        }

}
