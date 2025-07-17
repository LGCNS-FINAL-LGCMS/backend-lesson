package com.lgcms.lesson.repository;

import com.lgcms.lesson.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findAllByLessonId(String lessonId);
}
