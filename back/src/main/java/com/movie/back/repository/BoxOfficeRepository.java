package com.movie.back.repository;

import com.movie.back.entity.BoxOffice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoxOfficeRepository extends JpaRepository<BoxOffice,String> {

    public Page<BoxOffice> findAll(Pageable pageable);

    @Query("select distinct b from BoxOffice b " +
            "where  b.ranking is not null order by b.ranking asc  ")
    //fetch join 두개 존나위험해서 그냥 BatchSize사용함
    public List<BoxOffice> getBoxOfficeList();
    //left 없으면 null

    @Query("select distinct b from BoxOffice b left join fetch b.stillImage " +
            " where b.title LIKE %:title%")
    public BoxOffice getSerachMovie(@Param("title") String title);      //자세히보기

    @Query("select distinct b from BoxOffice b left join fetch b.stillImage" +
            " where b.title = :title")
    public BoxOffice getMovieRead(@Param("title")String title);

    @Query("select distinct b from BoxOffice b where b.title like %:title%")
    public Page<BoxOffice> getMovieList(@Param("title")String title,Pageable pageable);


    @Query("select distinct b from BoxOffice b join fetch b.likeGoods"+
            " where b.ranking is null order by size(b.likeGoods) desc")
    public List<BoxOffice> getLikeMovieList(Pageable pageable);

    @EntityGraph(attributePaths = "quizList")
    @Query("select b from BoxOffice b where b.title = :title")
    public BoxOffice getQuizeBoxOffice(@Param("title") String title);


    @EntityGraph(attributePaths = "quizList")
    @Query("select b from BoxOffice b order by size(b.quizList) desc ")
    public List<BoxOffice> getQuizeBoxOfficeOrderBy(Pageable pageable);

    @Query("select distinct b from BoxOffice b join fetch b.likeGoods order by size(b.likeGoods) desc ")
    public List<BoxOffice> getLikeList(Pageable pageable);

}
