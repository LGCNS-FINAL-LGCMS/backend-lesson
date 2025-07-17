package com.lgcms.lesson.domain.type;

public enum VideoStatus {
    ENCODING, //인코딩중
    DONE, //인코딩 완료 && S3 업로드 완료
    FAIL //인코딩 OR S3 업로드 실패
}
