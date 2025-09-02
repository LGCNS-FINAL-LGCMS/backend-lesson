package com.lgcms.lesson.repository;

import com.lgcms.lesson.domain.Lesson;
import com.lgcms.lesson.dto.response.lesson.LessonResponse;
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


    @Query("""
    SELECT l.id as id,
           l.title as title,
           l.lectureId as lectureId,
           l.videoUrl as videoUrl,
           l.thumbnailUrl as thumbnailUrl,
           l.information as information,
           l.createdAt as createdAt,
           l.playtime as playtime,
           lp.playtime as progressPlaytime
    FROM Lesson l
    LEFT JOIN LessonProgress lp 
           ON l.id = lp.lessonId AND lp.memberId = :memberId
    WHERE l.lectureId = :lectureId
    ORDER BY l.createdAt
""")
    List<LessonResponse> findLessonWithProgress(
            @Param("lectureId") String lectureId,
            @Param("memberId") Long memberId);
}
