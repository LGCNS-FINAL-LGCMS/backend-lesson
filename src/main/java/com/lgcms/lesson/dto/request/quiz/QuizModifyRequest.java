package com.lgcms.lesson.dto.request.quiz;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class QuizModifyRequest {

    private Long quizId;
    private String question;
    private char answer;
    private List<QuizAnswersRequest> answers;
}
