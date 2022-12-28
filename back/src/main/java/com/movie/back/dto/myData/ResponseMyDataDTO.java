package com.movie.back.dto.myData;

import com.movie.back.dto.MovieCommentsDTO;
import com.movie.back.dto.QuizDTO;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMyDataDTO {

    private Set<String> roleSet;

    private String email;

    private String birth;
    private String gender;

    private List<MyMovieData> myMovieDataList;

    private List<MyQuiz> quizDTOList;

    private List<MovieCommentsDTO> commentsDTOList;



}
