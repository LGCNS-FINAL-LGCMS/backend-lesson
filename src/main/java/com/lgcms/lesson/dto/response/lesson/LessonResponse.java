package com.lgcms.lesson.dto.response.lesson;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LessonResponse {

    private String id;

    private String title;

    private String lectureId;

    private Integer playtime;

    private String information;

    private String thumbnail;

    private String videoUrl;

    private LocalDateTime createdAt;

}
