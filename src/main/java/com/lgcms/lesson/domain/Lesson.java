package com.lgcms.lesson.domain;

import com.lgcms.lesson.domain.type.ImageStatus;
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
public class Lesson {

    @Id
    private String id;

    private String title;

    private String lectureId;

    private Long memberId;

    private String thumbnailUrl;

    @Column(columnDefinition = "text")
    private String information;

    private String videoUrl;

    private Integer playtime;

    @Enumerated(EnumType.STRING)
    private VideoStatus videoStatus;

    @Enumerated(EnumType.STRING)
    private ImageStatus imageStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public void modifyLesson(String information, String title){
       if(information != null && !information.isBlank()) this.information = information;
       if(title != null && !title.isBlank()) this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPlayTimeAndVideoAndThumbnail(Integer duration, String videoUrl, String thumbnailUrl){
        this.playtime = duration;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.videoStatus = VideoStatus.DONE;
        this.imageStatus = ImageStatus.DONE;
    }


}
