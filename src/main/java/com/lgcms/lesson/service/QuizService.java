package com.lgcms.lesson.service;

import com.lgcms.lesson.common.dto.exception.BaseException;
import com.lgcms.lesson.common.dto.exception.QuizError;
import com.lgcms.lesson.domain.Quiz;
import com.lgcms.lesson.domain.QuizAnswers;
import com.lgcms.lesson.dto.request.quiz.QuizAnswersRequest;
import com.lgcms.lesson.dto.request.quiz.QuizCreateRequest;
import com.lgcms.lesson.dto.request.quiz.QuizModifyRequest;
import com.lgcms.lesson.dto.response.quiz.QuizAnswerResponse;
import com.lgcms.lesson.dto.response.quiz.QuizResponse;
import com.lgcms.lesson.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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


    @Transactional
    public void modifyQuiz(String lessonId, List<QuizModifyRequest> list) {
        for(QuizModifyRequest quizModifyRequest : list){
            Quiz quiz = quizRepository.findById(quizModifyRequest.getQuizId())
                    .orElseThrow(() -> new BaseException(QuizError.QUIZ_NOT_FOUND));
            quiz.modifyQuiz(quizModifyRequest.getQuestion(), quizModifyRequest.getAnswer());

            quiz.getQuizAnswers().clear();

            for(QuizAnswersRequest quizAnswersRequest : quizModifyRequest.getAnswers()){
                QuizAnswers answers = QuizAnswers.builder()
                        .label(quizAnswersRequest.getLabel())
                        .content(quizAnswersRequest.getContent())
                        .build();
                quiz.addAnswer(answers);
            }
        }
    }

    @Transactional
    public List<QuizResponse> getQuizzes(String lessonId) {
        List<Quiz> quizzes = quizRepository.findAllByLessonId(lessonId);

        List<QuizResponse> responses = quizzes.stream()
                .map(quiz -> QuizResponse.builder()
                        .answer(quiz.getAnswer())
                        .question(quiz.getQuestion())
                        .answers(quiz.getQuizAnswers().stream()
                                .map(answer -> QuizAnswerResponse.builder()
                                        .label(answer.getLabel())
                                        .content(answer.getContent())
                                        .build()).toList()
                        ).build()).toList();
        return responses;
    }
}
