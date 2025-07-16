package com.lgcms.lesson.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonListResponseDto {

    private String title;

    private String information;

    private String thumbnail;

}
