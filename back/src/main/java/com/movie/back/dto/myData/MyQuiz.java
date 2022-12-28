package com.movie.back.dto.myData;


import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyQuiz {

    private Long id;
    private String movieTitle;

    private String quizName;

    private Set<String> quizItems;

}
