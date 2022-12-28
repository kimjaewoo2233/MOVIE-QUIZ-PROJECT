package com.movie.back.filter;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

//Authentication 해주는 곳 한번
@Slf4j
public class APILoginFilter extends AbstractAuthenticationProcessingFilter {

    public APILoginFilter(String defaultFilterProcessUrl){
        super(defaultFilterProcessUrl);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        log.info("APILOGINFIlter ========");

        if(request.getMethod().equalsIgnoreCase("GET")){
                log.info("GET METHOD NOT SUPPORT");
                return null;
        }
        Map<String,String> jsonData = parseRequestJSON(request);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(
                jsonData.get("email"),
                jsonData.get("password"));

        //token이 Authentication이다
        log.info(String.valueOf(jsonData));
        return getAuthenticationManager().authenticate(authenticationToken);    //AuthenticationManager연결필수
    }

    private Map<String, String> parseRequestJSON(HttpServletRequest request) {
        //JSON 데이터를 분석해서 mid. mpw 전달 값을 Map으로 처리
        try(Reader render = new InputStreamReader(request.getInputStream())){
            Gson gson = new Gson();

            return gson.fromJson(render,Map.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
