package com.movie.back.repository;

import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.Member;
import com.movie.back.entity.MemberMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberMovieRepository extends JpaRepository<MemberMovie,Long> {

        @Query("select distinct m.boxOfficeId from MemberMovie m where m.member.email = :email")
        public Page<BoxOffice> memberMyMovie(@Param("email") String email, Pageable pageable);

        @Modifying      //delete나 update는 이게 필요하다
        @Query("delete from MemberMovie m where m.boxOfficeId.title = :title and m.member.email = :email")
        public void deleteMyMovie(@Param("title") String title,@Param("email") String email);
        //like  부분과는 다르게 메소드를 사용함

        @Query("select count(m.id) > 0 from MemberMovie m where m.boxOfficeId.title = :title and m.member.email = :email")
        public boolean existysMyMovie(@Param("title") String title,@Param("email") String email);
}
