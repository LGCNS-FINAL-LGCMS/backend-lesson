package com.lgcms.lesson.service;

import com.lgcms.lesson.common.dto.exception.BaseException;
import com.lgcms.lesson.common.dto.exception.LessonError;
import com.lgcms.lesson.common.kafka.dto.EncodingSuccess;
import com.lgcms.lesson.common.kafka.dto.ProgressUpdate;
import com.lgcms.lesson.domain.Lesson;
import com.lgcms.lesson.domain.LessonProgress;
import com.lgcms.lesson.domain.Quiz;
import com.lgcms.lesson.domain.QuizAnswers;
import com.lgcms.lesson.domain.type.ImageStatus;
import com.lgcms.lesson.domain.type.VideoStatus;
import com.lgcms.lesson.dto.request.lesson.LessonCreateRequest;
import com.lgcms.lesson.dto.request.lesson.LessonModifyRequest;
import com.lgcms.lesson.dto.request.lesson.LessonProgressRequest;
import com.lgcms.lesson.dto.request.quiz.QuizAnswersRequest;
import com.lgcms.lesson.dto.request.quiz.QuizCreateRequest;
import com.lgcms.lesson.dto.response.lesson.LessonResponse;
import com.lgcms.lesson.event.producer.EncodingEventProducer;
import com.lgcms.lesson.event.producer.ProgressUpdateProducer;
import com.lgcms.lesson.repository.LessonProgressRepository;
import com.lgcms.lesson.repository.LessonRepository;
import com.lgcms.lesson.repository.QuizRepository;
import com.lgcms.lesson.service.internal.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LectureService lectureService;
    private final EncodingEventProducer encodingEventProducer;
    private final LessonProgressRepository lessonProgressRepository;
    private final ProgressUpdateProducer progressUpdateProducer;

    @Transactional
    public String registerLesson(LessonCreateRequest dto, String lectureId, Long memberId) {
        if (!lectureService.isLecturer(memberId, lectureId)) throw new BaseException(LessonError.LECTURE_FORBIDDEN);
        String lessonId = UUID.randomUUID() + dto.getTitle();

        Lesson lesson = Lesson.builder()
                .id(lessonId)
                .information(dto.getInformation())
                .title(dto.getTitle())
                .lectureId(lectureId)
                .memberId(memberId)
                .thumbnailUrl("https://cdn.lgcms.click/thumbnail/thumb_ing.jpg")
                .videoStatus(VideoStatus.ENCODING)
                .imageStatus(ImageStatus.ENCODING)
                .createdAt(LocalDateTime.now())
                .build();

        lessonRepository.save(lesson);

//        if (dto.getQuizzes() == null) return lesson.getId();

//        for (QuizCreateRequest quizCreateRequest : dto.getQuizzes()) {
//            Quiz quiz = Quiz.builder()
//                    .answer(quizCreateRequest.getAnswer())
//                    .lessonId(lesson.getId())
//                    .question(quizCreateRequest.getQuestion())
//                    .createdAt(LocalDateTime.now())
//                    .build();
//
//            for (QuizAnswersRequest quizAnswersRequest : quizCreateRequest.getAnswers()) {
//                QuizAnswers answer = QuizAnswers.builder()
//                        .label(quizAnswersRequest.getLabel())
//                        .content(quizAnswersRequest.getContent())
//                        .build();
//                quiz.addAnswer(answer);
//            }
//            quizRepository.save(quiz);
//        }
        return lesson.getId();
    }

    @Transactional
    public List<LessonResponse> getLessonTitles(String lectureId) {
        List<LessonResponse> lessons = lessonRepository.findAllByLectureIdOrderByCreatedAt(lectureId).stream()
                .map(lesson -> LessonResponse.builder()
                        .id(lesson.getId())
                        .title(lesson.getTitle())
                        .playtime(lesson.getPlaytime())
                        .createdAt(lesson.getCreatedAt())
                        .build())
                .toList();
        return lessons;
    }

    @Transactional
    public List<LessonResponse> getLessonList(String lectureId, Long memberId) {

//        List<LessonResponse> lessons = lessonRepository.findAllByLectureIdOrderByCreatedAt(lectureId).stream()
//                .map(lesson -> LessonResponse.builder()
//                        .id(lesson.getId())
//                        .title(lesson.getTitle())
//                        .lectureId(lesson.getLectureId())
//                        .videoUrl(lesson.getVideoUrl())
//                        .thumbnail(lesson.getThumbnailUrl())
//                        .information(lesson.getInformation())
//                        .createdAt(lesson.getCreatedAt())
//                        .playtime(lesson.getPlaytime())
//                        .build())
//                .toList();
//
        List<LessonResponse> lessons = lessonRepository.findLessonWithProgress(lectureId, memberId);
        if (lessons.isEmpty()) throw new BaseException(LessonError.LESSON_NOT_FOUND);

        return lessons;
    }

    @Transactional
    public String modifyLesson(String lessonId, Long memberId, LessonModifyRequest dto) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BaseException(LessonError.LESSON_NOT_FOUND));

        if (lesson.getMemberId() != memberId) throw new BaseException(LessonError.LECTURE_FORBIDDEN);

        lesson.modifyLesson(dto.getInformation(), dto.getTitle());

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
        if (!lectureService.isLecturer(memberId, lectureId)) throw new BaseException(LessonError.LECTURE_FORBIDDEN);
        lessonRepository.deleteAllByLectureId(lectureId);
    }

    @Transactional
    public LessonResponse getLesson(String lessonId, Long memberId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BaseException(LessonError.LESSON_NOT_FOUND));
        LessonProgress lessonProgress = lessonProgressRepository.findByLessonIdAndMemberId(lessonId, memberId);
        return LessonResponse.builder()
                .information(lesson.getInformation())
                .title(lesson.getTitle())
                .lectureId(lesson.getLectureId())
                .id(lesson.getId())
                .progress(lessonProgress.getPercentage())
                .videoUrl(lesson.getVideoUrl())
                .thumbnail(lesson.getThumbnailUrl())
                .createdAt(lesson.getCreatedAt())
                .playtime(lesson.getPlaytime())
                .build();
    }

    @Transactional
    public void updateVideoStatusAndThumbnail(EncodingSuccess videoEncodingSuccess) {
        Lesson lesson = lessonRepository.findById(videoEncodingSuccess.getLessonId())
                .orElseThrow(() -> new BaseException(LessonError.LESSON_NOT_FOUND));

        lesson.setPlayTimeAndVideoAndThumbnail(videoEncodingSuccess.getDuration(), videoEncodingSuccess.getVideoUrl(),
                videoEncodingSuccess.getThumbnailUrl());
        lessonRepository.save(lesson);
        List<LessonProgress> lessonProgressList = lessonProgressRepository.findByLectureIdAndMemberId(videoEncodingSuccess.getLectureId(), videoEncodingSuccess.getMemberId());

        Integer progress = lessonProgressList.stream()
                .mapToInt(LessonProgress::getPlaytime)
                .sum();
        videoEncodingSuccess.setDuration(progress);

        encodingEventProducer.EncodingSuccessEvent(videoEncodingSuccess);
    }

    @Transactional
    public void initLessonProgress(LessonProgressRequest lessonProgressRequest, Long memberId) {
        if (lessonProgressRepository.findByLessonIdAndMemberId(lessonProgressRequest.getLessonId(), memberId) != null)
            return;

        LessonProgress lessonProgress = LessonProgress.builder()
                .lessonId(lessonProgressRequest.getLessonId())
                .memberId(memberId)
                .lectureId(lessonProgressRequest.getLectureId())
                .totalPlaytime(lessonProgressRequest.getTotalPlaytime())
                .build();

        lessonProgressRepository.save(lessonProgress);
    }

    @Transactional
    public void updateLessonProgress(LessonProgressRequest lessonProgressRequest, Long memberId) {

        LessonProgress lessonProgress = lessonProgressRepository.findByLessonIdAndMemberId(lessonProgressRequest.getLessonId(), memberId);

        if (lessonProgress == null) {
            LessonProgress newLessonProgress = LessonProgress.builder()
                    .lessonId(lessonProgressRequest.getLessonId())
                    .lectureId(lessonProgressRequest.getLectureId())
                    .totalPlaytime(lessonProgressRequest.getTotalPlaytime())
                    .memberId(memberId)
                    .build();
            lessonProgressRepository.save(newLessonProgress);
            return;
        }

        lessonProgress.updatePlayTime(lessonProgressRequest.getPlaytime());
        lessonProgressRepository.save(lessonProgress);

        List<LessonProgress> lessonProgressList =
                lessonProgressRepository.findByLectureIdAndMemberId(lessonProgressRequest.getLectureId(), memberId);

        Integer progress = lessonProgressList.stream()
                .mapToInt(LessonProgress::getPlaytime)
                .sum();

        ProgressUpdate progressUpdate = ProgressUpdate.builder()
                .lessonId(lessonProgressRequest.getLessonId())
                .lectureId(lessonProgressRequest.getLectureId())
                .memberId(memberId)
                .playtime(progress)
                .build();

        progressUpdateProducer.updateProgressEvent(progressUpdate);
    }
}
