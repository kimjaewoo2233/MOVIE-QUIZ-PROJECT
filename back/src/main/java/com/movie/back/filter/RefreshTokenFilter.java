package com.movie.back.filter;

import com.google.gson.Gson;
import com.movie.back.security.exception.RefreshTokenException;
import com.movie.back.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {
    private final String refreshPath;

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        log.info("refreshPath=============================={}",refreshPath);
        if(!path.equals(refreshPath)){
            log.info("Skip refresh token filter......");
            filterChain.doFilter(request,response);
            return;
        }

        log.info("Refresh Token Filter ......run.............1");

        //전송된 JSON에서 accessToken과 refreshToken을 얻어온다.
        Map<String,String> tokens = parseRequestJSON(request);

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        log.info("accessTopken: "+accessToken);
        log.info("refreshToken: "+refreshToken);

        try{
            checkAccessToken(accessToken);
        }catch (RefreshTokenException refreshTokenException){
            refreshTokenException.sendResponseError(response);
            return;
        }

        Map<String,Object> refreshClaims = null;

        try{
            refreshClaims = checkRefreshToken(refreshToken);    //refershToken을 확인한다.
            log.info(String.valueOf(refreshClaims));
        }catch (RefreshTokenException refreshTokenException){
            refreshTokenException.sendResponseError(response);
            return;
        }

        //RefreshToken 의 유효시간이 얼마 남지 않은경우
        Integer exp = (Integer)refreshClaims.get("exp");    //이거 토큰 만료시간 exp

        Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);

        Date current = new Date(System.currentTimeMillis());

        //만료 시간과 현재 시간의 간 격을 계산한다.
        //만일 3일 미만인 경우에는 RefreshToken도 다시 생성한다.
        long gapTime = (expTime.getTime() - current.getTime());

        log.info("-----------------------------------------");
        log.info("current: " + current);
        log.info("expTime: " + expTime);
        log.info("gap: " + gapTime );

        String email = (String)refreshClaims.get("email");  //todo: 여기 닉네임도

        //이 상태까지 오면 무조건 AccessToken은 새로 생성하낟.
        String accessTokenValue = jwtUtil.generateToken(Map.of("email",email),1);
        String refreshTokenValue = tokens.get("refreshToken");

        //RefreshToken이 3일도 안 남으면
        if(gapTime < (1000 * 60 * 60 * 24 * 3)){
            log.info("new RefreshToken required...");
            refreshTokenValue = jwtUtil.generateToken(Map.of("email",email),30);
        }
        sendTokens(accessTokenValue,refreshTokenValue,response);
    }


    private void checkAccessToken(String accessToken) throws RefreshTokenException{
        try{
            jwtUtil.validateToken(accessToken);

        }catch (ExpiredJwtException exception){
            log.info("Access Token has expired");
        }catch (Exception e){
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS);
        }
    }

    //Refresh Token의 경우도 검사가 필요하다. RefreshToken이 존재하는지와
    //만료일이 지났는지를 확인하고 새로운 토큰 생성을 위해서 mid 값을얻어두도록 한다.
    //RefreshTokenFilter 내부에 checkRefreshToken()을 생성해서 문재가 생기면 RefreshTokenException을 발생
    //정상이라면 토늨 내용물들을 Map으로 반환하도록 구성한다.
    private Map<String,Object> checkRefreshToken(String refreshToken) throws RefreshTokenException{
        try{
            Map<String,Object> values = jwtUtil.validateToken(refreshToken);
            return values;
        }catch (ExpiredJwtException e){
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
        }catch(MalformedJwtException expiredJwtException){
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }catch (Exception e){
            new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }
        //이어서 적어야한다
        return null;
    }


    private Map<String,String> parseRequestJSON(HttpServletRequest request){
        //JSON 데이터를 분석해서 mid, mpw 전달 값을 Map으로 처리
        try(Reader reader = new InputStreamReader(request.getInputStream())){
            Gson gson = new Gson();
            return gson.fromJson(reader,Map.class); //request로 들어온 json데이터를 Map으로 바꾼다.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendTokens(String accesTokenValue,String refreshTokenValue, HttpServletResponse response){

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        String jsonStr = gson.toJson(
                Map.of("accessToken",accesTokenValue,"refreshToken",refreshTokenValue));

        try{
            response.getWriter().println(jsonStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
