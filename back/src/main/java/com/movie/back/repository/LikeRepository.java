package com.movie.back.repository;

import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.LikeGood;
import com.movie.back.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeGood,Long> {


    @Query("select count(l.id) > 0 from LikeGood l " +
            "where l.boxOffice = :title and  l.member = :email")
    boolean exists(@Param("title") BoxOffice title, @Param("email") Member email);

    @Query("select distinct l  from LikeGood l " +
            "left join fetch l.boxOffice join fetch l.member where l.boxOffice = :title and l.member = :email")
    Optional<LikeGood> likeElement(@Param("title") BoxOffice title, @Param("email") Member email);
    //이거 한 개만 가져오기에 패치 두번 문제없음


    @Query("delete from LikeGood l where l.boxOffice = :boxOffice and l.member = :member")
    void deleteLike(@Param("boxOffice") BoxOffice boxOffice,@Param("member") Member member);


    @Query("select l from LikeGood l where l.member.email in" +
            " (select m from Member m where m.ageGroup = :ageGroup)")
    List<LikeGood> likeGoodOrderBy(@Param("ageGroup") String ageGroup);

   // @EntityGraph(attributePaths = "boxOffice")
    @Query("select  l from LikeGood l left join fetch l.boxOffice " +
            "inner join l.member " +
            "where l.member.ageGroup = :ageGroup " +
            "order by size(l.boxOffice.likeGoods) desc ")
    List<LikeGood> likeGoodAgeGroup(@Param("ageGroup") String ageGroup);

//    @EntityGraph(attributePaths = "boxOffice")
//    @Query("select l from LikeGood l order by (select m.likeGoods from Member m where m.ageGroup = :ageGroup)")
//    List<LikeGood> likeGoodAgeGroup(@Param("ageGroup") String ageGroup);
}
