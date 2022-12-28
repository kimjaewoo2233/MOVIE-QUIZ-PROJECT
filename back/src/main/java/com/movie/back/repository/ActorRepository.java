package com.movie.back.repository;

import com.movie.back.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<ActorEntity,String> {
}
