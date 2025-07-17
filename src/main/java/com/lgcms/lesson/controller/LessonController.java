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

    //수강생(결제 완료한 사람)이 보는 페이지 결제 x인 회원이 볼때 어떻게 할지 고민
    @GetMapping("/details/{id}")
    public ResponseEntity<BaseResponse> getLessonList(@PathVariable String lectureId){
        List<LessonResponse> list = lessonService.getLessonList(lectureId);

        return ResponseEntity.ok(BaseResponse.ok(list));
    }


    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> modifyLesson(@PathVariable String lessonId, @RequestBody LessonModifyRequest data){
        Long memberId = Long.parseLong("1");
        String id = lessonService.modifyLesson(lessonId,memberId,data);

        return ResponseEntity.ok(BaseResponse.ok(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteLesson(@PathVariable String lessonId){
        Long memberId = Long.parseLong("1");
        lessonService.deleteLesson(lessonId,memberId);

        return ResponseEntity.ok(BaseResponse.ok("삭제완료~"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteAllLesson(@PathVariable String lectureId, @RequestParam("memberId") Long memberId){
        if(!lectureService.isExist(memberId, lectureId)) throw new BaseException(LessonError.LECTURE_FORBIDDEN);
        lessonService.deleteAllLesson(lectureId, memberId);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }
}
