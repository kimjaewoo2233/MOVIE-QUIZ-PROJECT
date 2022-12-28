package com.movie.back.init;

import com.movie.back.service.BoxOfficeService;
import com.movie.back.service.KobisScrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;

//@Service
@RequiredArgsConstructor
//@Component
public class InitService implements ApplicationListener<ContextRefreshedEvent> {
    private final BoxOfficeService boxOfficeService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        try {
            System.out.println("DB 값 주입실행");
//            boxOfficeService.saveBoxMovie();
//            boxOfficeService.saveSearchBoxOffice(2021,2022);
//        } catch (KobisScrapper.NotScrappedDateException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        /*



        * saveLike 연령별 라이크도 실행할 예정임 박스오피스가 아닌 영화 에서 대략추천 아니면 배포하고 해도 가능함
        *
        * */
    }
}
