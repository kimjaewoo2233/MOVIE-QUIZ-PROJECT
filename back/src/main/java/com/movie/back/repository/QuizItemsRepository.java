package com.movie.back.repository;

import com.movie.back.entity.QuizItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizItemsRepository extends JpaRepository<QuizItems,String> {
}
