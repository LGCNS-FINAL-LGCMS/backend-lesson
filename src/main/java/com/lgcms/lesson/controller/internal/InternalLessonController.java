package com.lgcms.lesson.controller.internal;

import com.lgcms.lesson.dto.response.lesson.LessonResponse;
import com.lgcms.lesson.repository.LessonRepository;
import com.lgcms.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/lesson")
@RequiredArgsConstructor
public class InternalLessonController {

    private final LessonService lessonService;

    @GetMapping("/title/{id}")
    public List<LessonResponse> getLessonList(@PathVariable("id") String lectureId){
        return lessonService.getLessonTitles(lectureId);
    }
}
