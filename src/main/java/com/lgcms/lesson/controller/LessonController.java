package com.lgcms.lesson.controller;


import com.lgcms.lesson.common.dto.BaseResponse;
import com.lgcms.lesson.common.dto.exception.BaseException;
import com.lgcms.lesson.common.dto.exception.LessonError;
import com.lgcms.lesson.dto.request.lesson.LessonRequestDto;
import com.lgcms.lesson.service.LectureService;
import com.lgcms.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController("/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final LectureService lectureService;

    @PostMapping("/{id}")
    public ResponseEntity<BaseResponse> registerLesson(@RequestPart("thumbnail") MultipartFile thumbnail,
                                                       @RequestPart("video") MultipartFile video,
                                                       @RequestPart("dto")LessonRequestDto dto,
                                                       @PathVariable("id") String lectureId
                                                       ){
        Long memberId = Long.parseLong("1");
        if(!lectureService.isExist(memberId, lectureId)) throw new BaseException(LessonError.LECTURE_FORBIDDEN);
        lessonService.registerLesson(dto,lectureId, memberId);

        return ResponseEntity.ok(BaseResponse.ok(null));
    }

}
