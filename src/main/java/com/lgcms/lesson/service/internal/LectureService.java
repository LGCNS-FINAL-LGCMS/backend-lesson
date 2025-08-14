package com.lgcms.lesson.service.internal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "RemoteLectureService" , url="/api/internal/lecture")
public interface LectureService {

    @GetMapping("/lecturer/verify")
    public Boolean isLecturer(@RequestParam("memberId") Long memberId, @RequestParam("lectureId") String lectureId);

    @GetMapping("/student/verify")
    public Boolean isStudent(@RequestParam("memberId") Long memberId, @RequestParam("lectureId") String lectureId);
}
