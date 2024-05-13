package com.patulus.becomeSpringDeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

// 엔티티로 지정
@Entity
// 엔티티 객체의 값을 간접적으로 조회할 수 있는 함수를 만드는 Lombok 어노테이션
@Getter
//@Setter
// 엔티티 클래스에는 Setter를 지양해야 한다.
// 문제점 1. 사용 의도를 파악하기 어려움: 게시글 값을 생성하는지, 변경하는지 의도 파악이 불분명
// 문제점 2. 일관성을 유지하기 어려움   : 의도치 않게 엔티티 객체의 값을 변경할 수 있음
// Setter를 통해 값이 변경되면 영속성 컨텍스트의 변경 감지 특성으로 Update 쿼리가 생성됨, 필드가 변경될 때마다 쿼리가 생성되므로 RDBMS의 부담이 큼
// 해결법 1. 사용한 의도나 의미를 알 수 있는 메서드를 작성: updateArticle(필드들) 메서드
// 해결법 2. 생성자를 통해 값을 넣어 일관성을 유지       : 생성 시점에 값을 넣음

// 같은 패키지와 해당 클래스를 상속 받은 클래스에서만 엔티티 객체를 생성할 수 있도록 함
// 접근 지시자가 protected인 파라미터가 없는 생성자를 만드는 Lombok 어노테이션
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 모든 필드를 파라미터로 하는 생성자를 만드는 Lombok 어노테이션 (access를 지정하지 않으면 접근 지시자는 public)
@AllArgsConstructor
public class Article {
    // id 필드를 기본 키로 지정
    @Id
    // 기본 키 자동 생성 설정 방식 지정
    // AUTO    : 데이터베이스의 방언에 따라 방식을 자동으로 선택
    //         * 데이터베이스 방언: RDBMS마다 자신만의 독자적인 기능을 가지는 것
    //
    // IDENTITY: 기본 키 생성을 데이터베이스에 위임
    //           MySQL처럼 Sequence를 제공하지 않고 AutoIncrement를 제공해 기본 키를 자동 생성하는 RDBMS에서 사용
    //           엔티티 객체를 데이터베이스에 저장한 후 기본 키 값을 조회할 수 있음
    //
    //           엔티티 객체를 처음 만들면 비영속 상태(엔티티 매니저가 관리하지 않는 상태)가 되고 엔티티 매니저의 persist() 메서드를 사용해 엔티티 객체를 관리 상태로 만들 수 있음
    //           이는 엔티티 객체가 영속성 컨텍스트에서 상태가 관리되는 것이며 그렇지 않기를 원하면 detach() 메서드로 엔티티를 분리 상태(준영속 상태)로 만들 수 있음
    //           엔티티 객체가 더 이상 필요 없으면 remove() 메서드를 사용해 엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제할 수 있음
    //         * 엔티티 매니저: 엔티티를 관리해 데이터베이스와 애플리케이션 간 객체의 생성, 수정, 삭제 등의 역할을 수행
    //                        엔티티 매니저는 엔티티를 관리하는 가상의 공간인 영속성 컨텍스트에 엔티티를 저장
    //
    //           엔티티가 관리 상태(영속 상태)가 되기 위해서는 식별자가 필요함, 식별자 생성은 DB에 등록해야 구할 수 있음
    //           트랜잭션 커밋 전 쿼리들을 모아 트랜잭션 커밋 시 한 번에 쿼리를 전송해 DBMS의 부담을 줄일 수 있는 쓰기 지연 방식이 동작하지 않음
    //         * 트랜잭션: 하나 이상의 SQL 문으로 이루어진 논리적인 작업 단위
    //         * 트랜잭션 커밋: 데이터를 테이블에 영구적으로 반영하는 것
    //
    // SEQUENCE: 데이터베이스 시퀀스를 사용해 기본 키를 할당, 주로 Oracle에서 사용
    //           엔티티 매니저의 persist() 메서드 호출 전 데이터베이스의 Sequence 객체에서 식별자를 조회한 후 이를 엔티티 객체에 할당 후 엔티티 객체를 관리 상태로 만듦 (1차 캐시에 등록됨)
    //           트랜잭션 커밋 후 플러시가 발생하면 해당 엔티티 객체를 데이터베이스에 저장
    //
    // TABLE   : 키 생성 테이블 사용
    //           키 생성 전용 테이블을 생성해 DB Sequence를 흉내냄
    //           식별자 조회 쿼리, 식별자 값 증가 쿼리 등을 사용하므로 데이터베이스와의 통신이 증가함
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Column 어노테이션이 없으면 필드 이름 그대로 DB Column과 매핑을 시도
    // DB Table의 Column 속성 지정
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    // Builder 패턴 방식으로 객체 생성
    // Builder 패턴 방식이 아니라면, Article("제목일까요?", "내용일까요?"); : 어떤 필드의 값인지 알기 어려움
    // Builder 패턴 방식이라면      Article.builder().title("제목이네요").content("내용이네요").build(); : 어떤 필드의 내용인지 알 수 있음
    @Builder
    private Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
