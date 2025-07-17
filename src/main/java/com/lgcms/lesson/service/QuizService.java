package com.lgcms.lesson.service;

import com.lgcms.lesson.domain.Quiz;
import com.lgcms.lesson.domain.QuizAnswers;
import com.lgcms.lesson.dto.request.quiz.QuizAnswersRequest;
import com.lgcms.lesson.dto.request.quiz.QuizCreateRequest;
import com.lgcms.lesson.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {
    private QuizRepository quizRepository;

    @Transactional
    public void registerQuiz(String lessonId, List<QuizCreateRequest> quizCreateRequests) {
        for (QuizCreateRequest request : quizCreateRequests) {
            Quiz quiz = Quiz.builder()
                    .answer(request.getAnswer())
                    .question(request.getQuestion())
                    .lessonId(lessonId)
                    .build();

            for (QuizAnswersRequest quizAnswersRequest : request.getAnswers()) {
                QuizAnswers quizAnswers = QuizAnswers.builder()
                        .label(quizAnswersRequest.getLabel())
                        .content(quizAnswersRequest.getContent())
                        .build();
                quiz.addAnswer(quizAnswers);
            }
            quizRepository.save(quiz);
        }
    }


}
