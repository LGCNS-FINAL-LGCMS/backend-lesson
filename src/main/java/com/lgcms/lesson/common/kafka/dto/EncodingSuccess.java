package com.lgcms.lesson.common.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncodingSuccess {

    private String lectureId;
    private String lessonId;
    private Long memberId;
    private String status;
    private Integer duration;
    private String videoUrl;
    private String thumbnailUrl;

}
