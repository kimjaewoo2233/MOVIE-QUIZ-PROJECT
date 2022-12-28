package com.movie.back.filter;


import com.movie.back.security.CustomUserDetailService;
import com.movie.back.security.exception.AccessTokenException;
import com.movie.back.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class TokenCheckFilter  extends OncePerRequestFilter {


    private final CustomUserDetailService customUserDetailService;
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        log.info(path);

//        if(!path.startsWith("/api/")){  //요청경로가 api로 시작하지 않으면 그냥 지나가게 함
//            filterChain.doFilter(request,response); //필터를 따라 다음 필터로
//            return;
//        }
        if(path.startsWith("/register") || path.startsWith("/mvi/") || path.startsWith("/search/") || path.startsWith("/login")){
            filterChain.doFilter(request,response);
            return;
        }
        log.info("Token Check Filter.....................");
        log.info("JWTUtil: "+jwtUtil);

        try{
            Map<String,Object> payload = validateAccessToken(request);

            if(path.startsWith("/quiz")){
              //  email 받기
                String email = (String)payload.get("email");
                System.out.println("현재 login한 email ==> "+ email);

                UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
                    //select 쿼리 한 번 실행할 수 밖에없음
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);


            }

            filterChain.doFilter(request,response);
        }catch (AccessTokenException accessTokenException){
            accessTokenException.sendResponseError(response);
        }
    }


    private Map<String,Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException{
        String headerStr = request.getHeader("Authorization");
        System.out.println(headerStr);
        if(headerStr == null || headerStr.length()<8){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }

        //Bearer 생략   Authorization: Bearer token 이런식으로 들어옴
        String tokenType = headerStr.substring(0,6);
        String tokenStr = headerStr.substring(7);

        if(tokenType.equalsIgnoreCase("Bearer") == false){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        try{
            Map<String,Object> values = jwtUtil.validateToken(tokenStr);
            System.out.println("토큰 값"+values);
            return values;
        }catch (MalformedJwtException malformedJwtException){
            log.info("MalformedJwtException--------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        }catch (SignatureException signatureException){
            log.info("SignatureException---------------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        }catch (ExpiredJwtException e){
            //AccessToken 만료되면 여기로감
            log.error("ExpiredJwtExcpetion----------------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
        /*JWTUtil validateToken을 실행해서 문제가 생기면 발생하는 JwtException을 이용해서 예외 내용을 출력하고  예외를 던짐*/


    }
}
