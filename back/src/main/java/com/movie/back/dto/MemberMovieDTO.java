package com.movie.back.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberMovieDTO {

        private Long id;

        private MemberDTO memberDTO;

        private BoxOfficeDTO boxOfficeDTO;

}
