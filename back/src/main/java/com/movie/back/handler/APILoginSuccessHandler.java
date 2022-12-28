package com.movie.back.handler;

import com.google.gson.Gson;
import com.movie.back.dto.MemberDTO;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Login Success Handler.................");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        log.info("authentication Handler   {}",authentication);
        log.info("authentication Handler   {}",authentication.getName());    //username임

        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();
        log.info("MemberDTO ======================= 여기{}",memberDTO);
        //TODO: 여기 닉네임부분수정예정
        Map<String,Object> claim = Map.of("email",authentication.getName());
        authentication.getPrincipal();
        //AccessToken 유효기간 1일
        String accessToken = jwtUtil.generateToken(claim,1);
        //RefreshToken 유효기간 30일
        String refreshToken = jwtUtil.generateToken(claim,30);

        Gson gson = new Gson();

        Map<String,String> keyMap = Map.of("accessToken",accessToken,"refreshToken",refreshToken);

        String jsonStr = gson.toJson(keyMap);

        response.getWriter().println(jsonStr);
        //request 객체를 보낸 곳으로 돌려준다.
    }
}
