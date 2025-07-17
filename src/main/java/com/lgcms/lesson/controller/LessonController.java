package com.lgcms.lesson.controller;


import com.lgcms.lesson.common.dto.BaseResponse;
import com.lgcms.lesson.common.dto.exception.BaseException;
import com.lgcms.lesson.common.dto.exception.LessonError;
import com.lgcms.lesson.dto.request.lesson.LessonCreateRequest;
import com.lgcms.lesson.dto.request.lesson.LessonModifyRequest;
import com.lgcms.lesson.dto.response.LessonResponse;
import com.lgcms.lesson.service.internal.LectureService;
import com.lgcms.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final LectureService lectureService;

    @PostMapping("/{id}")
    public ResponseEntity<BaseResponse> registerLesson(@RequestPart("dto") LessonCreateRequest dto,
                                                       @PathVariable("id") String lectureId){
        Long memberId = Long.parseLong("1");
        if(!lectureService.isExist(memberId, lectureId)) throw new BaseException(LessonError.LECTURE_FORBIDDEN);
        String lessonId = lessonService.registerLesson(dto,lectureId, memberId);

        return ResponseEntity.ok(BaseResponse.ok(lessonId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> modifyLesson(@PathVariable String lessonId, @RequestBody LessonModifyRequest data){
        Long memberId = Long.parseLong("1");
        String id = lessonService.modifyLesson(lessonId,memberId,data);

        return ResponseEntity.ok(BaseResponse.ok(id));
    }

}
