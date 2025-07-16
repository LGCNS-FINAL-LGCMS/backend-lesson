package com.lgcms.lesson.service;

import com.lgcms.lesson.domain.Lesson;
import com.lgcms.lesson.domain.type.VideoStatus;
import com.lgcms.lesson.dto.request.lesson.LessonRequestDto;
import com.lgcms.lesson.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonService {

    private LessonRepository lessonRepository;

    @Transactional
    public void registerLesson(LessonRequestDto dto, String lectureId, Long memberId) {
        String lessonId = UUID.randomUUID() + dto.getTitle();

        Lesson lesson = Lesson.builder()
                .id(lessonId)
                .information(dto.getInformation())
                .lectureId(lectureId)
                .memberId(memberId)
                .videoStatus(VideoStatus.ENCODING)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
