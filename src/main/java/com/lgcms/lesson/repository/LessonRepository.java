package com.lgcms.lesson.repository;

import com.lgcms.lesson.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {
    List<Lesson> findAllByLectureId(String lectureId);

    List<Lesson> findAllByLectureIdOrderById(String lectureId);

    void deleteAllByLectureId(String lectureId);
}
