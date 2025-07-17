package com.lgcms.lesson.common.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QuizError implements ErrorCodeInterface {
    Quiz_FORBIDDEN("LECE-03","강의에 접근 권한이 없습니다.", HttpStatus.FORBIDDEN);

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
