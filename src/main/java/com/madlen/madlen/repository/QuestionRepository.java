package com.madlen.madlen.repository;

import com.madlen.madlen.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query(value = "SELECT COUNT(q) FROM Question q WHERE :pageNumber = ANY(q.context_pages)", nativeQuery = true)
    Integer countQuestionsByPageNumber(int pageNumber);
}
