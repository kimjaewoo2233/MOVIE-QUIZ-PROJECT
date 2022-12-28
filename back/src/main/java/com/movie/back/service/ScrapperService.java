package com.movie.back.service;


import com.movie.back.data.cdata.Actor;
import com.movie.back.data.cdata.BoxOfficeData;
import com.movie.back.dto.ActorDTO;
import com.movie.back.dto.BoxOfficeDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScrapperService {


    public List<BoxOfficeDTO> latestBoxOffice() throws IOException, KobisScrapper.NotScrappedDateException {    //최신정보를 받음
        List<BoxOfficeDTO> list = new ArrayList<>();
        KobisScrapper kobisScrapper =
                new KobisScrapper(LocalDate.now().minusDays(7L), LocalDate.now().minusDays(2L));
        for(BoxOfficeData boxOfficeData : kobisScrapper.getBoxOfficesByDate(LocalDate.now().minusDays(2L))){
            System.out.println(boxOfficeData.getCode());
            String poster = KobisScrapper.getMainPosterByCode(boxOfficeData.getCode());
            String[] stillImage = kobisScrapper.getImageUrlsByCode(boxOfficeData.getCode(), KobisScrapper.ImageType.STILL_CUT,true);

            List<Actor> actorList = KobisScrapper.getActorList(boxOfficeData.getCode()).stream().filter(actor -> actor.getType().equalsIgnoreCase("주연")).collect(Collectors.toList());
            list.add(BoxOfficeDTO.builder().title(boxOfficeData.getTitle())
                    .rank(boxOfficeData.getRank())
                            .date(boxOfficeData.getDate().toString())
                            .actorList(actorList.stream().map(actor -> ActorDTO.builder().actorName(actor.getActorName())
                                    .actorRole(actor.getCharacterName()).build()).collect(Collectors.toList()))
                            .postLink(poster)
                            .stillImage(Arrays.asList(stillImage))
                            .synopsis(kobisScrapper.getSynopsisByCode(boxOfficeData.getCode()))
                    .build());
        }
        return list;
    }
}
