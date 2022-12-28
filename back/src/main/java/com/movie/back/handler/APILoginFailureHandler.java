package com.movie.back.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class APILoginFailureHandler implements AuthenticationFailureHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

//        String errorMessage = "로그인 실패";
//        if (exception instanceof BadCredentialsException) {
//            errorMessage = "Invalid Username / Password";
//        } else if (exception instanceof DisabledException) {
//            errorMessage = "Locked";
//        } else if (exception instanceof CredentialsExpiredException) {
//            errorMessage = "Expired password";
//        }



        objectMapper.writeValue(response.getWriter(), "Login Fail");
    }

}
