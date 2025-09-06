package com.lgcms.lesson.dto.request.lesson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonProgressRequest {

    private String lectureId;
    private String lessonId;
    private Integer playtime;
    private Integer totalPlaytime;
}
