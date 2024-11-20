package com.example.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Entity
@Table(name = "article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    
    @Column(name = "title", nullable = false)
    private String title = "";

    @Column(name = "content", nullable = false)
    private String content = "";

    @Builder
    public Article(String title, String content){
        this.title = title;
        this.content = content;
    }

    // 게시글 업데이트 메소드 추가
    public void update(String title, String content) { // 현재 객체 상태 업데이트
        this.title = title;
        this.content = content;
    }
        
}
