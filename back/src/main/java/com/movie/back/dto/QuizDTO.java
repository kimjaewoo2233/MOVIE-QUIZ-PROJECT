package com.movie.back.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDTO {
    private Long id;
    private String quizTitle;
    private String movieTitle;

    private List<QuizItems> quizItems;
    private String correct;
    private String key;

}
