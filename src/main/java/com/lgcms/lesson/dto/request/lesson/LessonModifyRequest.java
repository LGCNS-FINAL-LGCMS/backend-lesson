package com.lgcms.lesson.dto.request.lesson;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonModifyRequest {

    private String information;
}
