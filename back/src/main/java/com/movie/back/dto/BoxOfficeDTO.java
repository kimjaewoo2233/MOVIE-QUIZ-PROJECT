package com.movie.back.dto;

import com.movie.back.entity.ActorEntity;
import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.BoxStillImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxOfficeDTO {

        private String title;
        private Integer rank;
        private String postLink;
        private String synopsis;
        private String date;
        private double rating;
        private List<ActorDTO> actorList;
        private List<String> stillImage;


        public static BoxOffice toEntity(BoxOfficeDTO boxOfficeDTO){    //저장은 이놈이 하는게 아니기에 actorList와 sitll ㄴㄴ
                return BoxOffice.builder()                      //엔티티를 불러와서 거기에서 추가해야함
                        .title(boxOfficeDTO.getTitle())
                        .ranking(boxOfficeDTO.getRank())
                        .date(boxOfficeDTO.getDate())
                        .posterLink(boxOfficeDTO.getPostLink())
                        .synopsis(boxOfficeDTO.getSynopsis())
                        .build();
        }

}
