package com.movie.back.repository;

import com.movie.back.entity.MovieComments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieCommentRepository extends JpaRepository<MovieComments,Long> {

    public Page<MovieComments> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "rating")
    @Query("select c from MovieComments c where c.boxOffice.title = :title")
    public Page<MovieComments> findAllWithRating(Pageable pageable,@Param("title")String title);


    @EntityGraph(attributePaths = "rating")
    @Query("select c from MovieComments c where c.email = :email")
    public List<MovieComments> findAllWithRatingByUser(@Param("email")String email);

}
