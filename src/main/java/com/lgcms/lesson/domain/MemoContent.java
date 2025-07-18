package com.lgcms.lesson.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "memo_contents")
public class MemoContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id")
    private Memo memo;


    protected void setMemo(Memo memo){
        this.memo = memo;
    }
}
