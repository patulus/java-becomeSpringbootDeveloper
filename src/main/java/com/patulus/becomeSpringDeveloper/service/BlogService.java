package com.patulus.becomeSpringDeveloper.service;

import com.patulus.becomeSpringDeveloper.domain.Article;
import com.patulus.becomeSpringDeveloper.dto.AddArticleRequest;
import com.patulus.becomeSpringDeveloper.dto.UpdateArticleRequest;
import com.patulus.becomeSpringDeveloper.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    // @Transactional 애너테이션은 매칭한 메서드에서 발생하는 쿼리를 하나의 트랜잭션으로 묶음
    // * 트랜잭션: 쿼리 한 줄로는 해결할 수 없어 여러 개의 쿼리를 처리해야 하는 상황에서 문제가 생기면 시스템에 문제가 생길 수 있음
    //            대개 데이터베이스의 값을 추가, 변경, 삭제하는 메서드에는 @Transactional 애너테이션을 사용해 쿼리를 묶어 처리함
    @Transactional
    public Article update(long id, UpdateArticleRequest req) {
        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(req.getTitle(), req.getContent());

        return article;
    }

}
