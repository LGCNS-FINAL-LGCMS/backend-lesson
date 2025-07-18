package com.lgcms.lesson.controller;

import com.lgcms.lesson.common.dto.BaseResponse;
import com.lgcms.lesson.dto.request.memo.MemoCreateRequest;
import com.lgcms.lesson.dto.response.memo.MemoResponse;
import com.lgcms.lesson.service.MemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/memo")
@RequiredArgsConstructor
@Slf4j
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> registerOrGetMemo(@PathVariable("id") String lessonId){
        Long memberId = Long.parseLong("1");
        MemoResponse memoResponse = memoService.registerOrGetMemo(lessonId, memberId);

        return ResponseEntity.ok(BaseResponse.ok(memoResponse));
    }

    @PostMapping("/{id}")
    public ResponseEntity<BaseResponse> registerMemoContent(@PathVariable("id") String lessonId){
        Long memberId = Long.parseLong("1");
        memoService.registerMemo(lessonId, memberId);

        return ResponseEntity.ok(BaseResponse.ok(null));
    }


}
