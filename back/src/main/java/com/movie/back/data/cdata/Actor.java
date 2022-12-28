package com.movie.back.data.cdata;

import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class Actor {
        private String peopleNm;

        private String cast;

        private String actorGb;


        public String getActorName() {
            return peopleNm;
        }

        public String getCharacterName() {
            return cast;
        }

        public String getType() {
            switch (Integer.parseInt(actorGb)) {
                case 1:
                    return "주연";
                default:
                    return "없음";
            }
        }
}
