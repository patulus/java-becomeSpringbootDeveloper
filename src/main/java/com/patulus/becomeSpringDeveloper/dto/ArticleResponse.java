package com.patulus.becomeSpringDeveloper.dto;

import com.patulus.becomeSpringDeveloper.domain.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {

    private final String title;
    private final String content;

    // 생성자를 통한 final 필드 초기화
    // Article 엔티티로부터 제목과 내용을 가져와 필드에 저장
    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }

}
