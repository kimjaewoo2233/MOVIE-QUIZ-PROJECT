package com.movie.back.service;

import com.movie.back.dto.QuizDTO;
import com.movie.back.dto.QuizItems;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface QuizService {

    public List<QuizDTO> getQuiz(String title);

    public void saveQuiz(String movieTitle,String email,String quizTitle ,List<QuizItems> quizItems,String correct);

    public List<QuizItems> getItems(String quizTitle);

    public boolean getCheckQuiz(String item);

    public boolean getAddRoleQuiz(HttpServletRequest request);

    public boolean deleteQuizFeature(String id,String email);
}

