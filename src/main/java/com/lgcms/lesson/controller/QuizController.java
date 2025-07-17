package com.lgcms.lesson.controller;

import com.lgcms.lesson.common.dto.BaseResponse;
import com.lgcms.lesson.dto.request.quiz.QuizCreateRequest;
import com.lgcms.lesson.dto.request.quiz.QuizModifyRequest;
import com.lgcms.lesson.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/quiz")
@RequiredArgsConstructor
@Slf4j
public class QuizController {
    private final QuizService quizService;
    //
//    @GetMapping("/{id}")
//    public ResponseEntity<BaseResponse> getQuiz(@PathVariable("id") String lessonId){
//
//    }

    @PostMapping("/{id}")
    public ResponseEntity<BaseResponse> registerQuiz(@PathVariable("id") String lessonId,
                                                     @RequestBody List<QuizCreateRequest> list){
        quizService.registerQuiz(lessonId, list);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> modifyQuiz(@PathVariable("id") String lessonId,
                                                   @RequestBody List<QuizModifyRequest> list){
        quizService.modifyQuiz(lessonId, list);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }
}
