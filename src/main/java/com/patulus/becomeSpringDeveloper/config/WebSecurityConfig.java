package com.patulus.becomeSpringDeveloper.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import com.patulus.becomeSpringDeveloper.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    
    private final UserDetailsService userDetailsService;
    
    // 스프링 시큐리티 기능 비활성화
    // 정적 리소스, h2-console 하위 URL을 대상으로 인증 및 인가 서비스를 적용하지 않음
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
            .requestMatchers(toH2Console())
            .requestMatchers("/static/**");
    }
    
    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // login, singup, user 페이지로 요청이 오면 인증 및 인가가 없어도 접속할 수 있음
            .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/login", "/signup", "user").permitAll()
                .anyRequest().authenticated())
            // 폼 기반 로그인 설정
            // 로그인 페이지 경로 설정 및 로그인 성공 시 이동할 페이지 설정
            .formLogin((form) -> form.loginPage("/login")
                .defaultSuccessUrl("/articles"))
            // 로그아웃 성공 시 이동할 페이지 설정 및 로그아웃 이후 세션 삭제 여부 설정
            .logout((logout) -> logout.logoutSuccessUrl("/login").invalidateHttpSession(true))
            // CSRF 설정 비활성화
            // CSRF 공격 방지 위해 설정해야 하나 실습을 편리하게 하기 위해 비활성화
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }
    
    // 인증 관리자 관련 설정
    @Bean
    public DaoAuthenticationProvider authenticationManager(UserDetailsService userDetailsService) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // 사용자 서비스 설정
        // 이때 설정하는 서비스 클래스는 반드시 UserDetailsService를 상속받은 클래스여야 함
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        // 비밀번호를 암호화하기 위한 인코더 설정
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        
        return daoAuthenticationProvider;
    }
    
    // 패스워드 인코더를 빈으로 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
