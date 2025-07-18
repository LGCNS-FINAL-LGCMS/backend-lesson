package com.lgcms.lesson.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_answer")
@Getter@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private char label;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
