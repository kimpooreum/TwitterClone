package org.poopoo.twitterclone.config;

import lombok.RequiredArgsConstructor;
import org.poopoo.twitterclone.security.JwtAuthenticationFilter;
import org.poopoo.twitterclone.security.OAuth2SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 비활성화 (JWT 사용하므로 불필요)
            .csrf(csrf -> csrf.disable())

            // 요청 권한 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/auth/**",        // 일반 로그인/회원가입 API
                    "/h2-console/**",  // H2 콘솔 접근 허용
                    "/oauth2/**",      // GitHub OAuth 관련 요청
                    "/login/**"        // OAuth2 로그인 리다이렉트 URI
                ).permitAll()
                .anyRequest().authenticated() // 나머지는 인증 필요
            )

            // H2 콘솔 사용을 위한 FrameOptions 해제
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            // 세션을 STATELESS 로 설정 (JWT 사용)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 인증 제공자 등록
            .authenticationProvider(authenticationProvider)

            // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

            // OAuth2 로그인 설정
            .oauth2Login(oauth2 -> oauth2
                .successHandler(oAuth2SuccessHandler)
            );

        return http.build();
    }
}
