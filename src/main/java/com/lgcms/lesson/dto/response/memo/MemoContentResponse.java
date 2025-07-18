package com.lgcms.lesson.dto.response.memo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemoContentResponse {

    private Long id;

    private String content;
}
