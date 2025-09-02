package com.lgcms.lesson.service.internal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "RemoteLectureService")
public interface LectureService {

    @GetMapping("/internal/lecture/lecturer/verify")
    public Boolean isLecturer(@RequestParam("memberId") Long memberId, @RequestParam("lectureId") String lectureId);

    @GetMapping("/internal/lecture/student/verify")
    public Boolean isStudent(@RequestParam("memberId") Long memberId, @RequestParam("lectureId") String lectureId);
}
