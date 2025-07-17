package com.lgcms.lesson.dto.request.lesson;

import com.lgcms.lesson.dto.request.quiz.QuizCreateRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class LessonCreateRequest {
    private String title;

    private String information;

    private List<QuizCreateRequest> quizzes;
}
