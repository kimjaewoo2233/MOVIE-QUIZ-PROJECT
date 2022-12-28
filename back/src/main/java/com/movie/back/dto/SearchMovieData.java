package com.movie.back.dto;


import com.movie.back.entity.BoxOffice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchMovieData {

        private int totalPage;

        private List<BoxOfficeDTO> items;
}
