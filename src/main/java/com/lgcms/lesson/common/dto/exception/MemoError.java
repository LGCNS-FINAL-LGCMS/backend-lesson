package com.lgcms.lesson.common.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemoError implements ErrorCodeInterface {
    MEMO_NOT_FOUND("MEME-01","메모를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String status;
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.builder()
                .status(status)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }
}
