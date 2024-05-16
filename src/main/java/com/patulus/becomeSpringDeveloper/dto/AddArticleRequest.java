package com.patulus.becomeSpringDeveloper.dto;

// DAO(Data Access Object)  : 데이터베이스와 연결돼 데이터를 조회하고 수정하는 데 사용하는 객체
//                            직접 DB 접근해 삽입, 삭제, 조회 등 가능
// DTO(Data Transfer Object): 계층끼리 데이터를 교환하기 위해 사용하는 객체
//                            Getter, Setter 메서드만 가짐, 별도의 비즈니스 로직을 포함하지 않음
// VO(Value Object)         : 값 자체를 표현하는 객체
//                            객체의 해시 값이 달라도 값이 같으면 동일하다고 간주, 보통 Gatter 메서드만 가짐

import com.patulus.becomeSpringDeveloper.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    // toEntity(): Builder 패턴을 사용해 DTO를 엔티티로 만들어주는 메서드
    // 블로그에 글 추가 시 저장할 엔티티로 변환하는 용도로 사용
    public Article toEntity() {
        return Article.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }

}
