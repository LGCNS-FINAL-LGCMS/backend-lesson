package com.lgcms.lesson.repository;

import com.lgcms.lesson.domain.MemoContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoContentRepository extends JpaRepository<MemoContent, Long> {
}
