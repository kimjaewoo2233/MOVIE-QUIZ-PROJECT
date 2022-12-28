package com.movie.back.repository;

import com.movie.back.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {


    @EntityGraph(attributePaths = {"roleSet"})
    @Query("select m from Member m where m.email = :email")
    public Optional<Member> getMemberInfo(@Param("email") String email);


    @EntityGraph(attributePaths = {"movieSet"})
    @Query("select m from Member m where m.email = :email")
    public Optional<Member> getMyMovie(@Param("email") String email);

    @Query("select m from Member m where m.ageGroup = :ageGroup")
    public List<Member> memberAgeGroup(@Param("ageGroup") String ageGroup);
}
