package com.lgcms.lesson.service;

import com.lgcms.lesson.common.dto.exception.BaseException;
import com.lgcms.lesson.common.dto.exception.LessonError;
import com.lgcms.lesson.domain.Lesson;
import com.lgcms.lesson.domain.Quiz;
import com.lgcms.lesson.domain.QuizAnswers;
import com.lgcms.lesson.domain.type.ImageStatus;
import com.lgcms.lesson.domain.type.VideoStatus;
import com.lgcms.lesson.dto.request.lesson.LessonCreateRequest;
import com.lgcms.lesson.dto.request.lesson.LessonModifyRequest;
import com.lgcms.lesson.dto.request.quiz.QuizAnswersRequest;
import com.lgcms.lesson.dto.request.quiz.QuizCreateRequest;
import com.lgcms.lesson.dto.response.lesson.LessonResponse;
import com.lgcms.lesson.repository.LessonRepository;
import com.lgcms.lesson.repository.QuizAnswersRepository;
import com.lgcms.lesson.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonService {

    private LessonRepository lessonRepository;
    private QuizRepository quizRepository;
    private QuizAnswersRepository quizAnswersRepository;

    @Transactional
    public String registerLesson(LessonCreateRequest dto, String lectureId, Long memberId) {
        String lessonId = UUID.randomUUID() + dto.getTitle();

        Lesson lesson = Lesson.builder()
                .id(lessonId)
                .information(dto.getInformation())
                .title(dto.getTitle())
                .lectureId(lectureId)
                .memberId(memberId)
                .videoStatus(VideoStatus.ENCODING)
                .imageStatus(ImageStatus.ENCODING)
                .createdAt(LocalDateTime.now())
                .build();

        lessonRepository.save(lesson);

        if (dto.getQuizzes() == null) return lesson.getId();

        for (QuizCreateRequest quizCreateRequest : dto.getQuizzes()) {
            Quiz quiz = Quiz.builder()
                    .answer(quizCreateRequest.getAnswer())
                    .lessonId(lesson.getId())
                    .question(quizCreateRequest.getQuestion())
                    .createdAt(LocalDateTime.now())
                    .build();

            for (QuizAnswersRequest quizAnswersRequest : quizCreateRequest.getAnswers()) {
                QuizAnswers answer = QuizAnswers.builder()
                        .label(quizAnswersRequest.getLabel())
                        .content(quizAnswersRequest.getContent())
                        .build();
                quiz.addAnswer(answer);
            }
            quizRepository.save(quiz);
        }
        return lesson.getId();
    }

    @Transactional
    public List<LessonResponse> getLessonList(String lectureId) {
        List<LessonResponse> lessons = lessonRepository.findAllByLectureIdOrderByIdDESC(lectureId).stream()
                .map(lesson -> LessonResponse.builder()
                        .id(lesson.getId())
                        .title(lesson.getTitle())
                        .videoUrl(lesson.getVideoUrl())
                        .thumbnail(lesson.getThumbnailUrl())
                        .information(lesson.getInformation())
                        .createdAt(lesson.getCreatedAt())
                        .build())
                .toList();
        if (lessons.isEmpty()) throw new BaseException(LessonError.LESSON_NOT_FOUND);

        return lessons;
    }

    @Transactional
    public String modifyLesson(String lessonId, Long memberId, LessonModifyRequest dto) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BaseException(LessonError.LESSON_NOT_FOUND));

        if (lesson.getMemberId() != memberId) throw new BaseException(LessonError.LECTURE_FORBIDDEN);

        lesson.modifyLesson(dto.getInformation());

        return lesson.getId();
    }

    @Transactional
    public void deleteLesson(String lessonId, Long memberId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BaseException(LessonError.LESSON_NOT_FOUND));
        if (lesson.getMemberId() != memberId) throw new BaseException(LessonError.LECTURE_FORBIDDEN);

        lessonRepository.delete(lesson);
    }

    @Transactional
    public void deleteAllLesson(String lectureId, Long memberId) {
        lessonRepository.deleteAllByLectureId(lectureId);
    }

    @Transactional
    public LessonResponse getLesson(String lessonId) {
        //사용자 검증 해야함
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new BaseException(LessonError.LESSON_NOT_FOUND));

        return LessonResponse.builder()
                .information(lesson.getInformation())
                .videoUrl(lesson.getVideoUrl())
                .thumbnail(lesson.getThumbnailUrl())
                .createdAt(lesson.getCreatedAt())
                .id(lesson.getId())
                .build();
    }
}
