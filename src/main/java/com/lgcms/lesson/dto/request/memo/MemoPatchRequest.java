package com.lgcms.lesson.dto.request.memo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemoPatchRequest {
    private String content;
}
