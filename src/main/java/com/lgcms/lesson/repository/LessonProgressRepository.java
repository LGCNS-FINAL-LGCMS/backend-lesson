package com.lgcms.lesson.repository;

import com.lgcms.lesson.domain.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    LessonProgress findByLessonId(String lessonId);

    List<LessonProgress> findByLectureIdAndMemberId(String lectureId, Long memberId);

    LessonProgress findByLessonIdAndMemberId(String lessonId, Long memberId);
}
