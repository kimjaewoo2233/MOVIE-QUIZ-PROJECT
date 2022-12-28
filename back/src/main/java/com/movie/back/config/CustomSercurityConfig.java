package com.movie.back.config;


import com.movie.back.filter.APILoginFilter;
import com.movie.back.filter.RefreshTokenFilter;
import com.movie.back.filter.TokenCheckFilter;
import com.movie.back.handler.APILoginFailureHandler;
import com.movie.back.handler.APILoginSuccessHandler;
import com.movie.back.security.CustomUserDetailService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class CustomSercurityConfig {



    private final CustomUserDetailService customUserDetailService;

    private final JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());

        //Get AuthenticationManager
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.authenticationManager(authenticationManager);

        //APILoginFilter 추가
        APILoginFilter apiLoginFilter = new APILoginFilter("/generateToken");
        apiLoginFilter.setAuthenticationManager(authenticationManager);

        //APILoginSuccessHandler    이게 빈주입을 받는게 아니기때문에 jwtUtil은 이렇게 주입해야함
        APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtil);
        //Handler 세팅
        apiLoginFilter.setAuthenticationSuccessHandler(successHandler); //인증 성공하면 redirect 가 아닌 이 핸들러로 들어감
        //커스텀 필터 위치변경
        apiLoginFilter.setAuthenticationFailureHandler(new APILoginFailureHandler());
        //apiLoginFilter.setAuthenticationFailureHandler();
        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);   //인증필터

        http.addFilterBefore(tokenCheckFilter(jwtUtil,customUserDetailService),UsernamePasswordAuthenticationFilter.class); //모든 요청 필터
        //UsernamePassword를 처리하는 필터인 UsernamePasswrodAuthenticationFIlter 전에 커스텀필터를 둔다
        //refreshToken 처리
        http.addFilterBefore(new RefreshTokenFilter("/refreshToken",jwtUtil),TokenCheckFilter.class);

       // log.info("-------------------Web Configure-------------------");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);    //세션을 사용하지 않겠다는 것

        return http.build();
    }

    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil,CustomUserDetailService customUserDetailService){
        return new TokenCheckFilter(customUserDetailService,jwtUtil);
    }
}
