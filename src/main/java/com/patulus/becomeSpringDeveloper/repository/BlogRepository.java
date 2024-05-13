package com.patulus.becomeSpringDeveloper.repository;

import com.patulus.becomeSpringDeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data JPA의 공통 인터페이스를 상속 받아 JPQL 작성 없이 CRUD를 사용할 수 있음
// extends JpaRepository<엔티티 객체 타입, 기본 키 타입>
public interface BlogRepository extends JpaRepository<Article, Long> {
}
