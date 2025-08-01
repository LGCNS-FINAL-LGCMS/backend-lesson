package com.lgcms.lesson.controller;

import com.lgcms.lesson.common.dto.BaseResponse;
import com.lgcms.lesson.dto.request.memo.MemoCreateRequest;
import com.lgcms.lesson.dto.request.memo.MemoPatchRequest;
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

    //메모 있으면 메모 조회 없으면 등록 후 조회
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> registerOrGetMemo(@PathVariable("id") String lessonId){
        Long memberId = Long.parseLong("1");
        MemoResponse memoResponse = memoService.registerOrGetMemo(lessonId, memberId);

        return ResponseEntity.ok(BaseResponse.ok(memoResponse));
    }
    //메모 추가
    @PostMapping("/{id}")
    public ResponseEntity<BaseResponse> registerMemoContent(@PathVariable("id") String lessonId){
        Long memberId = Long.parseLong("1");
        memoService.registerMemo(lessonId, memberId);

        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    //메모 전체 조회
    @GetMapping("/member/{id}")
    public ResponseEntity<BaseResponse> getMemo(@PathVariable("id") String lessonId){
        Long memberId = Long.parseLong("1");
        MemoResponse memoResponse = memoService.getMemo(lessonId, memberId);

        return ResponseEntity.ok(BaseResponse.ok(memoResponse));
    }

    //메모 단건 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteMemo(@PathVariable("id") Long memoContentId){
        Long memberId = Long.parseLong("1");
        memoService.deleteMemo(memoContentId);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse> patchMemo(@PathVariable("id") Long memoId, MemoPatchRequest memoPatchRequest){
        memoService.patchMemo(memoId, memoPatchRequest);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

}
