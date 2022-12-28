package com.movie.back.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private List<Items> items;

    @Data
    @NoArgsConstructor
    static class Items{
        private String title;
        @JsonProperty("link")
        private String link;
        @JsonProperty("thumbnail")
        private String thumbnail;
    }


}
