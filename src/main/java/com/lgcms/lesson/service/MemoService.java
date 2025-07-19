package com.lgcms.lesson.service;

import com.lgcms.lesson.common.dto.exception.BaseException;
import com.lgcms.lesson.common.dto.exception.MemoError;
import com.lgcms.lesson.domain.Memo;
import com.lgcms.lesson.domain.MemoContent;
import com.lgcms.lesson.dto.request.memo.MemoCreateRequest;
import com.lgcms.lesson.dto.response.memo.MemoContentResponse;
import com.lgcms.lesson.dto.response.memo.MemoResponse;
import com.lgcms.lesson.repository.MemoContentRepository;
import com.lgcms.lesson.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemoService {
    private final MemoRepository memoRepository;
    private final MemoContentRepository memoContentRepository;

    //성능 고려 해봅시다
    @Transactional
    public MemoResponse registerOrGetMemo(String lectureId, Long memberId) {

        Optional<Memo> optionalMemo = memoRepository.findByLessonIdAndMemberId(lectureId, memberId);
        Memo memo = null;

        if(!optionalMemo.isPresent()){
            memo = Memo.builder()
                    .memberId(memberId)
                    .lessonId(lectureId)
                    .createdAt(LocalDateTime.now())
                    .build();
            MemoContent content = MemoContent.builder()
                    .build();

            memo.addMemoContent(content);

            memoRepository.save(memo);

        }else{
            memo = optionalMemo.get();
        }

        return MemoResponse.builder()
                .memoId(memo.getId())
                .contents(memo.getMemoContents().stream()
                        .map(contents -> MemoContentResponse.builder()
                                .id(contents.getId())
                                .content(contents.getContent())
                                .build()).toList()
                )
                .build();
    }


    @Transactional
    public void registerMemo(String lessonId, Long memberId) {
        Memo memo = memoRepository.findByLessonIdAndMemberId(lessonId, memberId)
                .orElseThrow(() -> new BaseException(MemoError.MEMO_NOT_FOUND));

        MemoContent memoContent = MemoContent.builder().build();
        memo.addMemoContent(memoContent);

    }

    @Transactional
    public MemoResponse getMemo(String lessonId, Long memberId) {
        Memo memo = memoRepository.findByLessonIdAndMemberId(lessonId, memberId)
                .orElseThrow(() -> new BaseException(MemoError.MEMO_NOT_FOUND));
        MemoResponse memoResponse = MemoResponse.builder()
                .memoId(memo.getId())
                .contents(memo.getMemoContents().stream()
                        .map(content -> MemoContentResponse.builder()
                                .id(content.getId())
                                .content(content.getContent()).build()
                        ).toList()
                ).build();

        return memoResponse;
    }

    @Transactional
    public void deleteMemo(Long memoId) {
        memoRepository.deleteById(memoId);
    }
}
