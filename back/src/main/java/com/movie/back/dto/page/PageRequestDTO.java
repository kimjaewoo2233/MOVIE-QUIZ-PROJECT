package com.movie.back.dto.page;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

        @Builder.Default
        private int page = 1;

        @Builder.Default
        private int size = 10;


}
