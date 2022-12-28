package com.movie.back.repository;

import com.movie.back.entity.Quiz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

        @Query("select q from Quiz q where q.boxOffice.title = :title")
        public List<Quiz> getQuiz(@Param("title") String title);


        @Query("select q from Quiz q where q.title = :quizTitle")
        public Optional<Quiz> getQuizByQuizTitle(@Param("quizTitle") String QuizTitle);

        //퀴즈 자세히보기
        @Query("select q from Quiz q join fetch q.quizItems where q.title = :quizTitle")
        public Optional<Quiz> quizDetail(@Param("quizTitle") String title);

        @Query("select q from Quiz q join fetch q.quizItems where q.id = :id")
        public Optional<Quiz> quizDetailById(@Param("id") Long id);


        @EntityGraph(attributePaths = "quizItems")
        @Query("select q from Quiz q where q.boxOffice.title = :title")
        public List<Quiz> getQuizByMovieTitle(@Param("title") String title);


        @EntityGraph(attributePaths = "quizItems")
        @Query("select q from Quiz q where q.email = :email")
        public List<Quiz> getQuizByEmail(@Param("email") String email);
}
