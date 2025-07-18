package com.lgcms.lesson.dto.response.quiz;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizAnswerResponse {

    private char label;
    private String content;
}
