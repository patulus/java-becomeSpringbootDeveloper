package com.patulus.becomeSpringDeveloper.blogApiController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patulus.becomeSpringDeveloper.domain.Article;
import com.patulus.becomeSpringDeveloper.dto.AddArticleRequest;
import com.patulus.becomeSpringDeveloper.dto.UpdateArticleRequest;
import com.patulus.becomeSpringDeveloper.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootApplication이 있는 클래스를 찾고 그 클래스에 포함된 빈을 찾은 다음 테스트용 스프링 컨테이너를 생성
@SpringBootTest
// MockMvc를 생성하고 자동으로 구성함
// * MockMvc: 애플리케이션을 서버에 배포하지 않고도 테스트용 MVC 환경을 만들어 요청, 전송, 응답을 제공하는 유틸리티 클래스
//            컨트롤러 테스트 시에 사용
@AutoConfigureMockMvc
public class BlogApiControllerTest {

    // 의존 관계 주입 (mockMvc = applicationContext.getBean("mockMvc", BeanType)과 동일한 효과)
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    // ObjectMapper 객체는 자바 객체를 JSON 데이터로 변환하는 직렬화
    // * 직렬화: 자바 객체를 외부에서 사용할 수 있도록 데이터를 변환하는 작업
    // 또는 JSON 데이터를 자바 객체로 변환하는 역직렬화할 때 사용
    // * 역직렬화: 외부에서 사용하는 데이터를 자바 객체로 변환하는 작업
    // ObjectMapper는 생성 비용이 비싸기 때문에 빈으로 등록하거나 static으로 처리하는 것이 좋음
//    beanDefinitionName = org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$Jackson2ObjectMapperBuilderCustomizerConfiguration
//    beanDefinitionName = standardJacksonObjectMapperBuilderCustomizer
//    beanDefinitionName = spring.jackson-org.springframework.boot.autoconfigure.jackson.JacksonProperties
//    beanDefinitionName = org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$JacksonObjectMapperBuilderConfiguration
//    beanDefinitionName = jacksonObjectMapperBuilder
//    beanDefinitionName = org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$ParameterNamesModuleConfiguration
//    beanDefinitionName = parameterNamesModule
//    beanDefinitionName = org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$JacksonObjectMapperConfiguration
//    beanDefinitionName = jacksonObjectMapper
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    // @BeforeAll: 클래스 레벨 설정 : DB 연결, 테스트 환경 초기화에 사용, 전체 테스트 실행 주기에서 한 번만 실행돼야 하므로 이 애너테이션이 적용된 메서드는 static이어야 함
    // @BeforeEach: 메서드 레벨 설정: 테스트 메서드에서 사용하는 객체의 초기화, 테스트에 필요한 값을 미리 넣을 때 사용
    // @Test: 테스트 실행
    // @AfterEach: 메서드 레벨 정리 : 테스트 이후 특정 데이터를 삭제할 때 사용
    // @AfterAll: 클래스 레벨 정리  : DB 연결 종료, 공통 자원 해제에 사용, @BeforeAll과 마찬가지로 메서드는 static이어야 함
    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository.deleteAll();
    }

    @Test
    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    public void addArticle() throws Exception {
        // given: 블로그 글 추가에 필요한 요청 객체를 만듭니다.
        final String url = "/api/articles";
        final String title = "제목인데스와";
        final String content = "내용인 것이와요 하와와";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        // 객체를 JSON으로 데이터 변환(직렬화)
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when : 블로그 글 추가 API에 요청을 보냅니다.
        //        이때 요청 타입은 JSON이며 given에서 미리 만든 객체를 요청 본문으로 함께 보냅니다.

        // MockMvc 클래스 내 ResultActions perform(RequestBuilder requestBuilder)
        // * MockMvc 요청 설정 메서드: MockMvcRequestBuilders 클래스 내 MockHttpServletRequestBuilder post(URI uri)
        //   - 다음 메서드의 체이닝이 가능 : https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockHttpServletRequestBuilder.html
        //                             : MockHttpServletRequestBuilder contentType(MediaType contentType) : 본문 타입 설정
        //                               ㄴ https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/MediaType.html
        //                             : MockHttpServletRequestBuilder content(String content)            : 요청 본문 설정
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then : 응답 코드가 201 CREATED인지 확인합니다.
        //        Blog를 전체 조회해 크기가 1인지 확인하고 실제 저장된 데이터와 요청 값을 비교합니다.
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
    public void findAllArticles() throws Exception {
        // given: 블로그 글을 저장합니다.
        final String url = "/api/articles";
        final String title = "제목인 것이와요";
        final String content = "경, 이 내용이 무슨 문제라도..?";

        blogRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        // when : 목록 조회 API를 호출합니다.
        final ResultActions resultActions = mockMvc.perform(
                get(url)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then : 응답 코드가 200 OK이고, 반환받은 값 중에 0번째 요소의 content와 title이 저장된 값과 같은지 확인합니다.
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @Test
    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    public void findArticle() throws Exception {
        // given: 블로그 글을 저장합니다.
        final String url = "/api/articles";
        final String title = "제목";
        final String content = "내용";

        Article savedArticle = blogRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        // when : 저장한 블로그 글의 id 값으로 API를 호출합니다.
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then : 응답 코드가 200 OK이고, 반환받은 content와 title이 저장된 값과 같은지 확인합니다.
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @Test
    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    public void deleteArticle() throws Exception {
        // given: 블로그 글을 저장한다.
        final String url = "/api/articles/{id}";
        final String title = "제목입니다.";
        final String content = "내용입니다.";

        Article savedArticle = blogRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        // when : 저장한 블로그 글을 id 값으로 삭제 API를 호출합니다.
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then : 응답 코드가 200 OK이고, Blog를 전체 조회해 크기가 0인지 확인합니다.
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
    public void updateArticle() throws Exception {
        // given: 블로그 글을 저장하고 글 수정에 필요한 요청 객체를 생성합니다.
        final String url = "/api/articles/{id}";
        final String title = "제목제목제목제목제목";
        final String content = "내용인것이었던것이었던것";

        Article savedArticle = blogRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        final String newTitle = "제목";
        final String newContent = "내용";

        UpdateArticleRequest req = new UpdateArticleRequest(newTitle, newContent);

        // when : 저장한 블로그 글을 id 값과 수정 객체로 수정 API를 호출합니다.
        ResultActions resultActions = mockMvc.perform(put(url, savedArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
        );

        // then : 응답 코드가 200 OK이고, 블로그 글 id로 조회한 후에 값이 수정되었는지 확인합니다.
        resultActions.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).orElseThrow(() -> new IllegalArgumentException("not found: " + savedArticle.getId()));

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }
}
