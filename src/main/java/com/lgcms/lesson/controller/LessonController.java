package com.lgcms.lesson.controller;


import com.lgcms.lesson.common.dto.BaseResponse;
import com.lgcms.lesson.common.dto.exception.BaseException;
import com.lgcms.lesson.common.dto.exception.LessonError;
import com.lgcms.lesson.dto.request.lesson.LessonCreateRequest;
import com.lgcms.lesson.dto.request.lesson.LessonModifyRequest;
import com.lgcms.lesson.dto.response.lesson.LessonResponse;
import com.lgcms.lesson.service.internal.LectureService;
import com.lgcms.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("/api")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/lecturer/lesson/{id}")
    public ResponseEntity<BaseResponse> registerLesson(@RequestBody LessonCreateRequest dto,
                                                       @PathVariable("id") String lectureId,
                                                       @RequestHeader("X-USER-ID") String memberId){
        String lessonId = lessonService.registerLesson(dto,lectureId, Long.parseLong(memberId));

        return ResponseEntity.ok(BaseResponse.ok(lessonId));
    }

    //수강생(결제 완료한 사람)이 보는 페이지 결제 x인 회원이 볼때 어떻게 할지 고민
    @GetMapping("/lesson/details/{id}")
    public ResponseEntity<BaseResponse<List<LessonResponse>>> getLessonList(@PathVariable String lectureId){
        List<LessonResponse> list = lessonService.getLessonList(lectureId);

        return ResponseEntity.ok(BaseResponse.ok(list));
    }

    @GetMapping("/lesson/section/{id}")
    public ResponseEntity<BaseResponse> getLesson(@PathVariable String lessonId){
        LessonResponse response = lessonService.getLesson(lessonId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }


    @PutMapping("lecturer/lesson/{id}")
    public ResponseEntity<BaseResponse> modifyLesson(@PathVariable String lessonId,
                                                     @RequestBody LessonModifyRequest data,
                                                     @RequestHeader("X-USER-ID") String memberId){
        String id = lessonService.modifyLesson(lessonId,Long.parseLong(memberId),data);

        return ResponseEntity.ok(BaseResponse.ok(id));
    }

    @DeleteMapping("lecturer/lesson/{id}")
    public ResponseEntity<BaseResponse> deleteLesson(@PathVariable String lessonId,
                                                     @RequestHeader("X-USER-ID") String memberId){
        lessonService.deleteLesson(lessonId,Long.parseLong(memberId));

        return ResponseEntity.ok(BaseResponse.ok("삭제완료~"));
    }

    @DeleteMapping("lecturer/lesson/delete/{id}")
    public ResponseEntity<BaseResponse> deleteAllLesson(@PathVariable String lectureId,
                                                        @RequestHeader("X-USER-ID") String memberId){
        lessonService.deleteAllLesson(lectureId, Long.parseLong(memberId));
        return ResponseEntity.ok(BaseResponse.ok(null));
    }
}
