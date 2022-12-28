package com.movie.back.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.movie.back.entity.MovieComments;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MovieCommentsDTO {

        private Long id;

        private String email;

        private String movieTitle;

        private String content;

        private boolean spoiler;

        private LocalDateTime createdAt;

        private String rating;

        private boolean blind;


}
