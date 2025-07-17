package com.lgcms.lesson.common.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LessonError implements ErrorCodeInterface {
    LECTURE_FORBIDDEN("LECE-03","강의에 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    LESSON_NOT_FOUND("LESE-01","공개된 강좌가 없습니다.", HttpStatus.NOT_FOUND)
    ;

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
