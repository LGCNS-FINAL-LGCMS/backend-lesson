package com.lgcms.lesson.dto.request.quiz;

import com.lgcms.lesson.domain.QuizAnswers;
import lombok.Data;

import java.util.List;

@Data
public class QuizCreateRequest {

    private String question;

    private char answer;

    private List<QuizAnswersRequest> answers;

}
