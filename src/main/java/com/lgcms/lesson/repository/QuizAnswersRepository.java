package com.lgcms.lesson.repository;

import com.lgcms.lesson.domain.QuizAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAnswersRepository extends JpaRepository<QuizAnswers, Long> {
}
