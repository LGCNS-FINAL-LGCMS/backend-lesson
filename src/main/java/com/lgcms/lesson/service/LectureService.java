package com.lgcms.lesson.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "lecture-service" , url="localhost:8080/internal/lecture")
public interface LectureService {

    @GetMapping("/verify")
    public Boolean isExist(@RequestParam("memberId") Long memberId, @RequestParam("lectureId") String lectureId);
}
