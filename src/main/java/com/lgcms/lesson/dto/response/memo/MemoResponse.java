package com.lgcms.lesson.dto.response.memo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemoResponse {

    private Long memoId;

    private List<MemoContentResponse> contents;
}
