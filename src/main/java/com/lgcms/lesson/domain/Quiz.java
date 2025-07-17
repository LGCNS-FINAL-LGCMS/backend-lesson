package com.lgcms.lesson.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lessonId;

    private String question;

    private char answer;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizAnswers> quizAnswers = new ArrayList<>();

    public void addAnswer(QuizAnswers answer) {
        this.quizAnswers.add(answer);
        answer.setQuiz(this);
    }

}
