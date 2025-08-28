package com.lgcms.lesson.common.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoInfo {

    private String lessonId;
    private String lectureId;
    private Integer duration;
    private String url;
}
