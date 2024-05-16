package com.patulus.becomeSpringDeveloper.service;

import com.patulus.becomeSpringDeveloper.domain.Article;
import com.patulus.becomeSpringDeveloper.dto.AddArticleRequest;
import com.patulus.becomeSpringDeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// final 또는 @NotNull이 있는 필드를 파라미터로 하는 생성자를 만드는 Lombok 어노테이션
// 여기서는 다음과 같은 생성자가 만들어진다: public BlogService(final BlogRepository blogRepository) { this.blogRepository = blogRepository; }
@RequiredArgsConstructor
// 스프링 빈으로 스프링 컨테이너에 등록
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    // save() 메서드는 JpaRepository에서 지원하는 저장 메서드로
    // AddArticleRequest 객체(DTO)에 저장된 값을 데이터베이스에 저장
    public Article save(AddArticleRequest req) {
        return blogRepository.save(req.toEntity());
    }

}
