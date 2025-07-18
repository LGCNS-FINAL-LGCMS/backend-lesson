package com.lgcms.lesson.repository;

import com.lgcms.lesson.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    Optional<Memo> findByLectureIdAndMemberId(String lectureId, Long memberId);

    Optional<Memo> findByLessonIdAndMemberId(String lectureId, Long memberId);
}
