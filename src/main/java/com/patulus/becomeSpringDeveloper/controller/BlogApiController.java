package com.patulus.becomeSpringDeveloper.controller;

import com.patulus.becomeSpringDeveloper.domain.Article;
import com.patulus.becomeSpringDeveloper.dto.AddArticleRequest;
import com.patulus.becomeSpringDeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
// HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RestController
public class BlogApiController {

    private final BlogService blogService;

    // HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest req) {
        Article savedArticle = blogService.save(req);

        // ResponseEntity: HTTP 요청 또는 응답의 헤더나 바디를 포함하는 클래스
        // 201 응답 코드를 날리고, HTTP 응답의 바디의 내용은 Article 객체를 JSON 형태로 전달
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

}
