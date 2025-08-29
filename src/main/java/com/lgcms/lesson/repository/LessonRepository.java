package com.lgcms.lesson.repository;

import com.lgcms.lesson.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {
    List<Lesson> findAllByLectureId(String lectureId);

    @Query("""
        SELECT l
        FROM Lesson l
        WHERE l.lectureId = :lectureId
        ORDER BY l.createdAt
        """)
    List<Lesson> findAllByLectureIdOrderByIdCUSTOM(@Param("lectureId") String lectureId);

    List<Lesson> findAllByLectureIdOrderByCreatedAt(String lectureId);

    void deleteAllByLectureId(String lectureId);
}
