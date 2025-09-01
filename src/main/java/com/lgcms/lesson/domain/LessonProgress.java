package com.lgcms.lesson.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String lectureId;

    private String lessonId;

    private Integer playtime;

    private Integer totalPlaytime;

    private Integer lastWatched;

    private Integer percentage;

    private LocalDateTime updatedAt;

    @PreUpdate
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void initProgress() {
        this.percentage = 0;
        this.playtime = 0;
        this.lastWatched = 0;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePlayTime(int playtime) {
        if (this.playtime < playtime) this.playtime = playtime;
        this.lastWatched = playtime;

        double ratio = (double) this.playtime / this.totalPlaytime * 100;
        int percent = (int) Math.round(ratio);

        this.percentage = (percent >= 97) ? 100 : percent;
    }

}
