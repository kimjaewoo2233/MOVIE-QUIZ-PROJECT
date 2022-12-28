package com.movie.back.repository;

import com.movie.back.entity.MovieRating;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;

import javax.persistence.Entity;
import java.util.List;

public interface RatingRepository extends JpaRepository<MovieRating,Long> {


    @Query("select r.rating from MovieRating r where r.boxOffice.title = :title")
    public List<Double> getRatingByTitle(@Param("title") String title);

    @EntityGraph(attributePaths = "member")
    @Query("select r from MovieRating r where r.member.email = :email and r.boxOffice.title = :title")
    public MovieRating getRating(@Param("email") String email,@Param("title") String title);



}
