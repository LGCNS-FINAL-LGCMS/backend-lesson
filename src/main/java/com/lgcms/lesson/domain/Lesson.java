package com.lgcms.lesson.domain;

import com.lgcms.lesson.domain.type.VideoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lecture_item")
public class Lesson {

    @Id
    private String id;

    private String title;

    private String lectureId;

    private Long memberId;

    private String thumbnailUrl;

    private String information;

    private String videoUrl;

    @Enumerated(EnumType.STRING)
    private VideoStatus videoStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public void modifyLesson(String information){
        this.information = information;
        this.updatedAt = LocalDateTime.now();
    }


}
