package com.lgcms.lesson.dto.response.quiz;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuizResponse {

    private String question;

    private char answer;

    private List<QuizAnswerResponse> answers;
}
